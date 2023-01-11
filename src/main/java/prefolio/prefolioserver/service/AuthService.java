package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.dto.UserInfoDTO;

@Service
public interface AuthService {

    UserInfoDTO.Response saveUserInfo(UserInfoDTO.Request userInfoRequest);
}
