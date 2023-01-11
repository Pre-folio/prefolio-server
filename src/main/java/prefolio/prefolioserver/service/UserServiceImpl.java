package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.User;
import prefolio.prefolioserver.dto.UserInfoDTO;
import prefolio.prefolioserver.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserInfoDTO.Response saveUserInfo(UserInfoDTO.Request userInfoRequest) {
        System.out.println("AuthServiceImpl.saveUserInfo");
        User user = User.builder()
                .type(userInfoRequest.getType())
                .nickname(userInfoRequest.getNickname())
                .profileImage(userInfoRequest.getProfileImage())
                .grade(userInfoRequest.getGrade())
                .refreshToken(userInfoRequest.getRefreshToken())
                .build();
        System.out.println("user Entity = " + user.getNickname());
        User savedUser = userRepository.saveAndFlush(user);
        return new UserInfoDTO.Response(savedUser);
    }
}
