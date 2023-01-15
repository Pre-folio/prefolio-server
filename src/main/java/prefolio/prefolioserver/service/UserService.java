package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.dto.CheckUserDTO;

@Service
public interface UserService {

    UserJoinDTO.Response joinUser(UserJoinDTO.Request userJoinRequest);

    //CheckUserDTO.Response checkUser(String nickname);
    CheckUserDTO.Response findUserByNickname(CheckUserDTO.Request checkUserRequest);
    GetUserInfoDTO.Response getUserInfo(Long userId);
}
