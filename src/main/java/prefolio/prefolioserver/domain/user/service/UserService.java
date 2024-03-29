package prefolio.prefolioserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import prefolio.prefolioserver.domain.post.domain.Like;
import prefolio.prefolioserver.domain.post.domain.Scrap;
import prefolio.prefolioserver.domain.post.dto.request.TokenRequestDTO;
import prefolio.prefolioserver.domain.user.domain.User;
import prefolio.prefolioserver.domain.user.dto.request.CheckUserRequestDTO;
import prefolio.prefolioserver.domain.user.dto.request.UserInfoRequestDTO;
import prefolio.prefolioserver.domain.user.dto.response.GetUserInfoResponseDTO;
import prefolio.prefolioserver.domain.user.dto.response.CheckUserResponseDTO;
import prefolio.prefolioserver.domain.user.dto.response.TokenResponseDTO;
import prefolio.prefolioserver.domain.user.dto.response.UserInfoResponseDTO;
import prefolio.prefolioserver.global.error.CustomException;
import prefolio.prefolioserver.domain.post.query.LikeSpecification;
import prefolio.prefolioserver.domain.post.query.ScrapSpecification;
import prefolio.prefolioserver.domain.user.repository.UserRepository;
import prefolio.prefolioserver.domain.post.repository.ScrapRepository;
import prefolio.prefolioserver.domain.post.repository.LikeRepository;

import java.util.*;

import static prefolio.prefolioserver.global.error.ErrorCode.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final LikeRepository likeRepository;
    private final JwtTokenService jwtTokenService;


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

    public CheckUserResponseDTO findUserByNickname(CheckUserRequestDTO checkUserRequest) {
        User user = userRepository.findById(checkUserRequest.getUserId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Optional<User> findUser = userRepository.findByNickname(checkUserRequest.getNickname());
        if (findUser.isEmpty() || Objects.equals(findUser.get().getId(), checkUserRequest.getUserId())) {
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

        Specification<Like> likeSpec = (root, query, criteriaBuilder) -> null;
        Specification<Scrap> scrapSpec = (root, query, criteriaBuilder) -> null;

        likeSpec = likeSpec.and(LikeSpecification.likeCount(user));
        scrapSpec = scrapSpec.and(ScrapSpecification.likeCount(user));

        List<Like> countLike = likeRepository.findAll(likeSpec);
        Long likeNum = (long) countLike.size();

        List<Scrap> countScrap = scrapRepository.findAll(scrapSpec);
        Long scrapNum = (long) countScrap.size();

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

            String accessToken = jwtTokenService.buildAccessToken(user.getId(), issuedAt, accessTokenExpiresIn);

            jwtTokenService.getAuthentication(accessToken);
            return new TokenResponseDTO(accessToken);
        }
        else return new TokenResponseDTO("null");
    }
}
