package prefolio.prefolioserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import prefolio.prefolioserver.domain.OAuth;
import prefolio.prefolioserver.dto.KakaoLoginDTO;
import prefolio.prefolioserver.dto.KakaoUserInfoDTO;
import prefolio.prefolioserver.repository.AuthRepository;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthRepository authRepository;
    private final UserDetailsService userDetailsService;

    @Value("${kakao.key}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.uri")
    private String KAKAO_REDIRECT_URI;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Override
    public KakaoLoginDTO.Response kakaoLogin(String code) {

        // "인가 코드"로 "accessToken" 요청
        String kakaoAccessToken = getAccessToken(code);
        log.trace("kakao access token = {}", kakaoAccessToken);

        // 토큰으로 카카오 API 호출 (이메일 정보 가져오기)
        KakaoUserInfoDTO userInfo = getUserInfo(kakaoAccessToken);
        log.trace("userInfo = {}", userInfo);

        // DB정보 확인 -> 없으면 DB에 저장
        OAuth user = registerOAuthIfNeed(userInfo);
        log.trace("user = {}", user);

        // 로그인 처리
        Authentication authentication = getAuthentication(user);
        log.trace("authentication = {}", authentication);

        // JWT 토큰 리턴
        String jwtToken = usersAuthorizationInput(authentication);
        log.trace("jwtToken = {}", jwtToken);

        return new KakaoLoginDTO.Response(jwtToken);
    }

    // 인가코드로 accessToken 요청
    private String getAccessToken(String code) {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        System.out.println("header>>>");
        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", KAKAO_CLIENT_ID);
        body.add("redirect_uri", KAKAO_REDIRECT_URI);
        body.add("code", code);
        System.out.println("body>>>");

        // HTTP 요청 보내기 - Post 방식
        // response 변수의 응답 받음
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        System.out.println("HTTP 요청 보내기");
        System.out.println("response = " + response);

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("access_token").asText();
        } catch (JsonProcessingException e) {
            log.error("error log = kakao access token 요청에 대한 응답 발생 실패");
            return null;
        }

    }

    // 토큰으로 카카오 API 호출
    private KakaoUserInfoDTO getUserInfo(String accessToken) {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer" + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기 - Post 방식
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // responseBody 정보 꺼내기
        String responseBody = response.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            String email = jsonNode.get("kakao_account").get("email").asText();
            log.trace("email = {}", email);
            return new KakaoUserInfoDTO(email);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    // DB정보 확인 -> 없으면 DB에 저장
    private OAuth registerOAuthIfNeed(KakaoUserInfoDTO userInfo) {
        // DB에 중복된 이메일 있는지 확인
        String kakaoEmail = userInfo.getEmail();
        OAuth user = authRepository.findByEmail(kakaoEmail)
                .orElse(null);

        // DB에 없을 시 DB에 추가
        if (user == null) {
            // DB에 정보 등록
            log.info("DB에 user 정보 없음 -> 추가 진행");
            user = new OAuth(kakaoEmail, false);
            authRepository.save(user);
        }
        return user;
    }

    // 로그인 처리
    private Authentication getAuthentication(OAuth user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    // JWT 토큰 리턴
    private String usersAuthorizationInput(Authentication authentication) {
        UserDetails userDetails = ((UserDetails) authentication.getPrincipal());
        String token = generateJwtToken(userDetails);
        return token;
    }

    @Override
    public String generateJwtToken(UserDetails userDetails) {
        OAuth user = authRepository.findByEmail(userDetails.getUsername()).get();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);  // 만료일 1일

        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn = new Date(cal.getTimeInMillis());

        return buildAccessToken(user.getId(), issuedAt, accessTokenExpiresIn);
    }

    private String buildAccessToken(Long id, Date issuedAt, Date accessTokenExpiresIn) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(id.toString())
                .setIssuer("prefolio")
                .setIssuedAt(issuedAt)
                .setExpiration(accessTokenExpiresIn)
                .claim("id", id)
                .claim("roles", "USER")
                .signWith(
                        SignatureAlgorithm.HS256,
                        Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(StandardCharsets.UTF_8))
                )
                .compact();
    }
}
