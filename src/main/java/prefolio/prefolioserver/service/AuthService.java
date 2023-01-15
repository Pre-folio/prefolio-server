package prefolio.prefolioserver.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.dto.KakaoLoginDTO;

@Service
public interface AuthService {

    public KakaoLoginDTO.Response kakaoLogin(String code);
    public String generateJwtToken(UserDetailsImpl userDetails);
}
