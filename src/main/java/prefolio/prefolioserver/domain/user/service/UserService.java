package prefolio.prefolioserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.post.dto.request.TokenRequestDTO;
import prefolio.prefolioserver.domain.post.repository.LikeRepository;
import prefolio.prefolioserver.domain.post.repository.ScrapRepository;
import prefolio.prefolioserver.domain.user.domain.User;
import prefolio.prefolioserver.domain.user.dto.request.CheckUserRequestDTO;
import prefolio.prefolioserver.domain.user.dto.request.UserInfoRequestDTO;
import prefolio.prefolioserver.domain.user.dto.response.CheckUserResponseDTO;
import prefolio.prefolioserver.domain.user.dto.response.GetUserInfoResponseDTO;
import prefolio.prefolioserver.domain.user.dto.response.TokenResponseDTO;
import prefolio.prefolioserver.domain.user.dto.response.UserIdResponseDTO;
import prefolio.prefolioserver.domain.user.helper.UserHelper;
import prefolio.prefolioserver.domain.user.mapper.UserMapper;
import prefolio.prefolioserver.domain.user.repository.UserRepository;
import prefolio.prefolioserver.global.config.jwt.TokenProvider;
import prefolio.prefolioserver.global.config.user.UserDetails;

import java.util.Calendar;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final LikeRepository likeRepository;
    private final TokenProvider tokenProvider;
    private final UserHelper userHelper;
    private final UserMapper userMapper;


    public UserIdResponseDTO setUserInfo(UserDetails authUser, UserInfoRequestDTO userInfoRequest) {
        User user = authUser.getUser();
        user.updateInfo(userInfoRequest);

        User savedUser = userRepository.saveAndFlush(user);
        return userMapper.toUserIdResponse(savedUser.getId());
    }

    public CheckUserResponseDTO findUserByNickname(CheckUserRequestDTO checkUserRequest) {
        userHelper.findUser(checkUserRequest.getUserId());
        String nickname = checkUserRequest.getNickname();

        return userMapper.toCheckUserResponse(userHelper.duplicatedNickname(nickname));
    }

    public UserIdResponseDTO getUserId(UserDetails authUser) {
        User user = authUser.getUser();

        return userMapper.toUserIdResponse(user.getId());
    }

    public GetUserInfoResponseDTO getUserInfo(Long userId) {
        User user = userHelper.findUser(userId);

        Long countLike = likeRepository.countByUser(user);
        Long countScrap = scrapRepository.countByUser(user);

        return userMapper.toGetUserInfoResponse(user, countLike, countScrap);
    }

    public TokenResponseDTO getNewToken(TokenRequestDTO refreshToken, Long userId) {
        User user = userHelper.findUser(userId);

        //레디스 로직 추가
        if (Objects.equals(user.getRefreshToken(), refreshToken.getRefreshToken())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, 3);  // 만료시간 1시간

            Authentication authentication = tokenProvider.usersAuthorizationInput(user);
            String accessToken = tokenProvider.generateJwtAccessToken(user.getId(), authentication);

            return new TokenResponseDTO(accessToken);
        }
        else return new TokenResponseDTO("null");
    }
}
