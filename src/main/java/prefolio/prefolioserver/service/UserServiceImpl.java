package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import prefolio.prefolioserver.domain.Like;
import prefolio.prefolioserver.domain.OAuth;
import prefolio.prefolioserver.domain.Scrap;
import prefolio.prefolioserver.domain.User;
import prefolio.prefolioserver.dto.CheckUserDTO;
import prefolio.prefolioserver.dto.GetUserInfoDTO;
import prefolio.prefolioserver.dto.UserJoinDTO;
import prefolio.prefolioserver.repository.AuthRepository;
import prefolio.prefolioserver.repository.UserRepository;
import prefolio.prefolioserver.repository.ScrapRepository;
import prefolio.prefolioserver.repository.LikeRepository;

import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.valueOf;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final ScrapRepository scrapRepository;
    private final LikeRepository likeRepository;

    @Override
    public UserJoinDTO.Response joinUser(UserJoinDTO.Request userJoinRequest) {
        User user = User.builder()
                .type(userJoinRequest.getType())
                .nickname(userJoinRequest.getNickname())
                .profileImage(userJoinRequest.getProfileImage())
                .grade(userJoinRequest.getGrade())
                .refreshToken(userJoinRequest.getRefreshToken())
                .build();
        OAuth oauth = OAuth.builder()
                .isMember(TRUE)
                .build();
        //System.out.println("user Entity = " + user.getNickname());
        User savedUser = userRepository.saveAndFlush(user);
        authRepository.saveAndFlush(oauth);
        return new UserJoinDTO.Response(savedUser);
    }

    @Override
    public CheckUserDTO.Response findUserByNickname(CheckUserDTO.Request checkUserRequest) {
        Optional<User> user = userRepository.findByNickname(checkUserRequest.getNickname());
        if (user.isEmpty()) {
            return new CheckUserDTO.Response(false);
        }
        return new CheckUserDTO.Response(true);
    }

    @Override
    public GetUserInfoDTO.Response getUserInfo(Long userId){
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty())
            return new GetUserInfoDTO.Response();

        Optional<Long> countScrap = scrapRepository.countByUserId(userId);
        Optional<Long> countLike = likeRepository.countByUserId(userId);

        if(countScrap.isEmpty() && countLike.isEmpty())
            return new GetUserInfoDTO.Response(user.get(),0L,0L);

    return new GetUserInfoDTO.Response(user.get(), countScrap.get(), countLike.get());
    }
}
