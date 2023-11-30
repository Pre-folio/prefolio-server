package prefolio.prefolioserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.user.domain.User;
import prefolio.prefolioserver.domain.user.dto.KakaoUserInfoDTO;
import prefolio.prefolioserver.domain.user.dto.response.KakaoLoginResponseDTO;
import prefolio.prefolioserver.domain.user.helper.KakaoHelper;
import prefolio.prefolioserver.global.config.jwt.TokenProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService{

    private final TokenProvider tokenProvider;
    private final KakaoHelper kakaoHelper;

    public KakaoLoginResponseDTO kakaoLogin(String code) {

        final String kakaoAccessToken = kakaoHelper.getAccessToken(code);
        final KakaoUserInfoDTO userInfo = kakaoHelper.getUserInfo(kakaoAccessToken);
        User user = kakaoHelper.registerUserIfNeed(userInfo);

        Authentication authentication = tokenProvider.usersAuthorizationInput(user);
        final String accessToken = tokenProvider.generateJwtAccessToken(user.getId(), authentication);
        final String refreshToken = tokenProvider.generateJwtRefreshToken(user.getId(), authentication);

        final Boolean isMember = kakaoHelper.checkIsMember(user);

        return new KakaoLoginResponseDTO(user.getId(), accessToken, refreshToken, isMember);
    }
}
