package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.dto.response.KakaoLoginResponseDTO;

@Service
public interface AuthService {

    public KakaoLoginResponseDTO kakaoLogin(String code);
    public String generateJwtToken(UserDetailsImpl userDetails);
}
