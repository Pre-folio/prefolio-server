package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import prefolio.prefolioserver.domain.User;
import prefolio.prefolioserver.dto.CommonResponseDTO;
import prefolio.prefolioserver.dto.request.CheckUserRequestDTO;
import prefolio.prefolioserver.dto.request.TokenRequestDTO;
import prefolio.prefolioserver.dto.request.UserInfoRequestDTO;
import prefolio.prefolioserver.dto.response.GetUserInfoResponseDTO;
import prefolio.prefolioserver.dto.response.CheckUserResponseDTO;
import prefolio.prefolioserver.dto.response.TokenResponseDTO;
import prefolio.prefolioserver.dto.response.UserInfoResponseDTO;
import prefolio.prefolioserver.error.CustomException;
import prefolio.prefolioserver.repository.UserRepository;
import prefolio.prefolioserver.repository.ScrapRepository;
import prefolio.prefolioserver.repository.LikeRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static prefolio.prefolioserver.error.ErrorCode.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final LikeRepository likeRepository;
    private final KakaoService kakaoService;


    public UserInfoResponseDTO setUserInfo(UserDetailsImpl authUser, UserInfoRequestDTO UserInfoRequest) {

        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        findUser.setType(UserInfoRequest.getType());
        findUser.setNickname(UserInfoRequest.getNickname());
        findUser.setProfileImage(UserInfoRequest.getProfileImage());
        findUser.setGrade(UserInfoRequest.getGrade());
        findUser.setCreatedAt(new Date());

        User savedUser = userRepository.saveAndFlush(findUser);
        return new UserInfoResponseDTO(savedUser);
    }

    public CheckUserResponseDTO findUserByNickname(UserDetailsImpl authUser, CheckUserRequestDTO checkUserRequest) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Optional<User> findUser = userRepository.findByNickname(checkUserRequest.getNickname());
        if (findUser.isEmpty() || findUser.get().getId() == user.getId()) {
            return new CheckUserResponseDTO(false);
        }
        return new CheckUserResponseDTO(true);
    }

    public UserInfoResponseDTO getUserId(UserDetailsImpl authUser) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return new UserInfoResponseDTO(user.getId());
    }

    public GetUserInfoResponseDTO getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Optional<Long> countScrap = scrapRepository.countByUserId(userId);
        Optional<Long> countLike = likeRepository.countByUserId(userId);

        Long scrapNum = countScrap.isEmpty() ? 0L : countScrap.get();
        Long likeNum = countLike.isEmpty() ? 0L : countLike.get();

        return new GetUserInfoResponseDTO(user, scrapNum, likeNum);
    }

    public TokenResponseDTO getNewToken(TokenRequestDTO refreshToken, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (Objects.equals(user.getRefreshToken(), refreshToken.getRefreshToken())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, 3);  // 만료시간 1시간

            final Date issuedAt = new Date();
            final Date accessTokenExpiresIn = new Date(cal.getTimeInMillis());

            String accessToken = kakaoService.buildAccessToken(user.getId(), issuedAt, accessTokenExpiresIn);

            kakaoService.getAuthentication(accessToken);
            return new TokenResponseDTO(accessToken);
        }
        else return new TokenResponseDTO("null");
    }
}
