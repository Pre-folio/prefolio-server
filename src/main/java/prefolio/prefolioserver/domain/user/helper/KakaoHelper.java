package prefolio.prefolioserver.domain.user.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import prefolio.prefolioserver.domain.user.domain.User;
import prefolio.prefolioserver.domain.user.dto.KakaoUserInfoDTO;
import prefolio.prefolioserver.domain.user.exception.MismatchCode;
import prefolio.prefolioserver.domain.user.repository.UserRepository;
import prefolio.prefolioserver.global.error.exception.TokenValidate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KakaoHelper {

    @Value("${kakao.key}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.uri}")
    private String KAKAO_REDIRECT_URI;

    private final UserRepository userRepository;

    public String getAccessToken(String code) {
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
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            throw MismatchCode.EXCEPTION;
        }

    }

    public KakaoUserInfoDTO getUserInfo(String accessToken) {
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

    public User registerUserIfNeed(KakaoUserInfoDTO userInfo) {
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

    public Boolean checkIsMember(User user) {
        return user.getNickname() != null;
    }
}
