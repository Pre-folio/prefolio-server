package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.dto.UserInfoDTO;

@Service
public interface UserService {

    UserInfoDTO.Response saveUserInfo(UserInfoDTO.Request userInfoRequest);
}
