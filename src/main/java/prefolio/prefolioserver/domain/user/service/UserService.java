package prefolio.prefolioserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
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
import prefolio.prefolioserver.domain.user.exception.UserNotFound;
import prefolio.prefolioserver.global.config.user.UserDetails;
import prefolio.prefolioserver.domain.post.query.LikeSpecification;
import prefolio.prefolioserver.domain.post.query.ScrapSpecification;
import prefolio.prefolioserver.domain.user.repository.UserRepository;
import prefolio.prefolioserver.domain.post.repository.ScrapRepository;
import prefolio.prefolioserver.domain.post.repository.LikeRepository;
import prefolio.prefolioserver.global.config.jwt.TokenProvider;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final LikeRepository likeRepository;
    private final TokenProvider tokenProvider;


    public UserInfoResponseDTO setUserInfo(UserDetails authUser, UserInfoRequestDTO userInfoRequest) {

        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> UserNotFound.EXCEPTION);


        findUser.updateInfo(userInfoRequest);

        User savedUser = userRepository.saveAndFlush(findUser);
        return new UserInfoResponseDTO(savedUser);
    }

    public CheckUserResponseDTO findUserByNickname(CheckUserRequestDTO checkUserRequest) {
        User user = userRepository.findById(checkUserRequest.getUserId())
                .orElseThrow(() -> UserNotFound.EXCEPTION);
        Optional<User> findUser = userRepository.findByNickname(checkUserRequest.getNickname());
        if (findUser.isEmpty() || Objects.equals(findUser.get().getId(), checkUserRequest.getUserId())) {
            return new CheckUserResponseDTO(false);
        }
        return new CheckUserResponseDTO(true);
    }

    public UserInfoResponseDTO getUserId(UserDetails authUser) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> UserNotFound.EXCEPTION);
        return new UserInfoResponseDTO(user.getId());
    }

    public GetUserInfoResponseDTO getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFound.EXCEPTION);

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
                .orElseThrow(() -> UserNotFound.EXCEPTION);

        //레디스 로직 추가
        if (Objects.equals(user.getRefreshToken(), refreshToken.getRefreshToken())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, 3);  // 만료시간 1시간

            final Date issuedAt = new Date();
            final Date accessTokenExpiresIn = new Date(cal.getTimeInMillis());

            Authentication authentication = tokenProvider.usersAuthorizationInput(user);
            String accessToken = tokenProvider.generateJwtAccessToken(user.getId(), authentication);

            return new TokenResponseDTO(accessToken);
        }
        else return new TokenResponseDTO("null");
    }
}
