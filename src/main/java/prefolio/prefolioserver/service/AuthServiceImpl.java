package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.User;
import prefolio.prefolioserver.dto.UserInfoDTO;
import prefolio.prefolioserver.repository.AuthRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthRepository authRepository;


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
        User savedUser = authRepository.saveAndFlush(user);
        return new UserInfoDTO.Response(savedUser);
    }
}
