package prefolio.prefolioserver.domain.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import prefolio.prefolioserver.domain.user.domain.User;
import prefolio.prefolioserver.domain.user.dto.response.KakaoLoginResponseDTO;
import prefolio.prefolioserver.domain.user.dto.KakaoUserInfoDTO;
import prefolio.prefolioserver.domain.user.exception.MismatchCode;
import prefolio.prefolioserver.domain.user.repository.UserRepository;
import prefolio.prefolioserver.global.config.jwt.TokenProvider;
import prefolio.prefolioserver.global.error.exception.TokenValidate;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService{

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Value("${kakao.key}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.uri}")
    private String KAKAO_REDIRECT_URI;


    public KakaoLoginResponseDTO kakaoLogin(String code) {

        // "인가 코드"로 "accessToken" 요청
        String kakaoAccessToken = getAccessToken(code);

        // 토큰으로 카카오 API 호출 (이메일 정보 가져오기)
        KakaoUserInfoDTO userInfo = getUserInfo(kakaoAccessToken);

        // DB정보 확인 -> 없으면 DB에 저장
        User user = registerUserIfNeed(userInfo);

        // 로그인 처리
        // JWT 토큰 리턴
        final Authentication authentication = tokenProvider.userAuthorizationInput(user);

        final String accessToken = tokenProvider.generateJwtAccessToken(user.getId(), authentication);
        final String refreshToken = tokenProvider.generateJwtRefreshToken(user.getId(), authentication);

        // 회원여부 닉네임으로 확인
        Boolean isMember = checkIsMember(user);

        return new KakaoLoginResponseDTO(user.getId(), accessToken, refreshToken, isMember);
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
        } catch (Exception e) {
            throw MismatchCode.EXCEPTION;
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
        } catch (Exception e) {
            throw TokenValidate.EXCEPTION;
        }
    }

    // DB정보 확인 -> 없으면 DB에 저장
    private User registerUserIfNeed(KakaoUserInfoDTO userInfo) {
        // DB에 중복된 이메일 있는지 확인
        String kakaoEmail = userInfo.getEmail();
        Optional<User> user = userRepository.findByEmail(kakaoEmail);

        if (user.isEmpty()) {
            // DB에 정보 등록
            User newUser = User.builder().email(kakaoEmail)
                    .build();
            userRepository.save(newUser);
        }

        return userRepository.findByEmail(kakaoEmail).get();
    }

    // 회원 여부 확인
    private Boolean checkIsMember(User user) {
        if(user.getNickname() != null) {
            return true;
        }
        return false;
    }

}
