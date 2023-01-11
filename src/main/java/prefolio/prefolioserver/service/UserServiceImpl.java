package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.OAuth;
import prefolio.prefolioserver.domain.User;
import prefolio.prefolioserver.dto.CheckUserDTO;
import prefolio.prefolioserver.dto.UserInfoDTO;
import prefolio.prefolioserver.repository.AuthRepository;
import prefolio.prefolioserver.repository.UserRepository;

import java.util.Optional;

import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    @Override
    public UserInfoDTO.Response saveUserInfo(UserInfoDTO.Request userInfoRequest) {
        User user = User.builder()
                .type(userInfoRequest.getType())
                .nickname(userInfoRequest.getNickname())
                .profileImage(userInfoRequest.getProfileImage())
                .grade(userInfoRequest.getGrade())
                .refreshToken(userInfoRequest.getRefreshToken())
                .build();
        OAuth oauth = OAuth.builder()
                .isMember(TRUE)
                .build();
        System.out.println("user Entity = " + user.getNickname());
        User savedUser = userRepository.saveAndFlush(user);
        authRepository.saveAndFlush(oauth);
        return new UserInfoDTO.Response(savedUser);
    }


    @Override
    public CheckUserDTO.Response checkUser(String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);
//        return user.map(value -> new CheckUserDTO.Response(value.getNickname()))
//                .orElseGet(() -> new CheckUserDTO.Response("null"));
        if (user.isEmpty()) {
            //new CheckUserDTO.Response().setIs_used(false);
            return new CheckUserDTO.Response(false);
        }
        return new CheckUserDTO.Response(true);
    }

}
