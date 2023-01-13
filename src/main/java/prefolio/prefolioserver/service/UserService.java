package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.dto.CheckUserDTO;
import prefolio.prefolioserver.dto.GetUserInfoDTO;
import prefolio.prefolioserver.dto.UserJoinDTO;

@Service
public interface UserService {

    UserJoinDTO.Response joinUser(UserJoinDTO.Request userJoinRequest);

    //CheckUserDTO.Response checkUser(String nickname);
    CheckUserDTO.Response findUserByNickname(CheckUserDTO.Request checkUserRequest);
    GetUserInfoDTO.Response getUserInfo(Long userId);
}
