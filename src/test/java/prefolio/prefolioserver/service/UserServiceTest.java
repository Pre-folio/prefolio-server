package prefolio.prefolioserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import prefolio.prefolioserver.domain.User;
import prefolio.prefolioserver.dto.request.CheckUserRequestDTO;
import prefolio.prefolioserver.dto.request.JoinUserRequestDTO;
import prefolio.prefolioserver.dto.response.CheckUserResponseDTO;
import prefolio.prefolioserver.dto.response.GetUserInfoResponseDTO;
import prefolio.prefolioserver.dto.response.JoinUserResponseDTO;
import prefolio.prefolioserver.error.CustomException;
import prefolio.prefolioserver.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static prefolio.prefolioserver.error.ErrorCode.USER_NOT_FOUND;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Test
    @Transactional
    void joinUser() {
        User user = userRepository.findById(16L).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        JoinUserRequestDTO request = new JoinUserRequestDTO("dev", "sss", "jsjlgejlg", 2);
        UserDetailsImpl authUser = userDetailsService.loadUserByUsername("16");
        JoinUserResponseDTO response = userService.joinUser(authUser, request);

        System.out.println("response = " + response.toString());
    }

    @Test
    @DisplayName("닉네임 있을 때")
    void findUserByNicknameTrue() {
        CheckUserRequestDTO checkUserRequestDTO = new CheckUserRequestDTO("승희");
        CheckUserResponseDTO checkUserResponseDTO = userService.findUserByNickname(checkUserRequestDTO);
        System.out.println("checkUserResponseDTO = " + checkUserResponseDTO.getIs_used());
    }

    @Test
    @DisplayName("닉네임 없을 때")
    void findUserByNicknameFalse() {
        CheckUserRequestDTO request = new CheckUserRequestDTO("수현");
        CheckUserResponseDTO response = userService.findUserByNickname(request);
        System.out.println("response.getIs_used() = " + response.getIs_used());
    }

    @Test
    void getUserInfo() {
        GetUserInfoResponseDTO response = userService.getUserInfo(15L);
        System.out.println("response.getNickname() = " + response.getNickname());
    }
}