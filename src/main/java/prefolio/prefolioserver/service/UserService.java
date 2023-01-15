package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prefolio.prefolioserver.dto.request.CheckUserRequestDTO;
import prefolio.prefolioserver.dto.request.JoinUserRequestDTO;
import prefolio.prefolioserver.dto.response.GetUserInfoResponseDTO;
import prefolio.prefolioserver.dto.response.CheckUserResponseDTO;
import prefolio.prefolioserver.dto.response.JoinUserResponseDTO;


@Service
public interface UserService {


    @Transactional
    JoinUserResponseDTO joinUser(JoinUserRequestDTO joinUserRequest);

    CheckUserResponseDTO findUserByNickname(CheckUserRequestDTO checkUserRequest);
    GetUserInfoResponseDTO getUserInfo(Long userId);
}
