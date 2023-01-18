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
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import prefolio.prefolioserver.domain.User;
import prefolio.prefolioserver.dto.response.KakaoLoginResponseDTO;
import prefolio.prefolioserver.dto.KakaoUserInfoDTO;
import prefolio.prefolioserver.service.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService {

    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenServiceImpl jwtTokenService;

    @Value("${kakao.key}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.uri}")
    private String KAKAO_REDIRECT_URI;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Override
    public KakaoLoginResponseDTO kakaoLogin(String code) {

        // "인가 코드"로 "accessToken" 요청
        String kakaoAccessToken = getAccessToken(code);

        // 토큰으로 카카오 API 호출 (이메일 정보 가져오기)
        KakaoUserInfoDTO userInfo = getUserInfo(kakaoAccessToken);

        // DB정보 확인 -> 없으면 DB에 저장
        User user = registerUserIfNeed(userInfo);

        // JWT 토큰 리턴
        String jwtToken = usersAuthorizationInput(user);

        // 로그인 처리
        Authentication authentication = getAuthentication(jwtToken);

        return new KakaoLoginResponseDTO(jwtToken);
    }

    // 인가코드로 accessToken 요청
    private String getAccessToken(String code) {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", KAKAO_CLIENT_ID);
        body.add("redirect_uri", KAKAO_REDIRECT_URI);
        body.add("code", code);

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

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("access_token").asText();
        } catch (JsonProcessingException e) {
            return null;
        }

    }

    // 토큰으로 카카오 API 호출
    private KakaoUserInfoDTO getUserInfo(String accessToken) {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
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
            return new KakaoUserInfoDTO(email);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    // DB정보 확인 -> 없으면 DB에 저장
    private User registerUserIfNeed(KakaoUserInfoDTO userInfo) {
        // DB에 중복된 이메일 있는지 확인
        String kakaoEmail = userInfo.getEmail();
        User user = userRepository.findByEmail(kakaoEmail)
                .orElse(null);

        // DB에 없을 시 DB에 추가
        if (user == null) {
            // DB에 정보 등록
            user = User.builder().email(kakaoEmail)
                            .build();
            userRepository.save(user);
        }
        return user;
    }

    // JWT 토큰 리턴
    private String usersAuthorizationInput(User user) {
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(String.valueOf(user.getId()));
        String token = generateJwtToken(userDetails);
        return token;
    }

    // 로그인 처리
    private Authentication getAuthentication(String token) {
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(String.valueOf(
                jwtTokenService.getUserIdFromJwtToken(token)));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Override
    public String generateJwtToken(UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail()).get();
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
