package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import prefolio.prefolioserver.domain.User;
import prefolio.prefolioserver.dto.request.CheckUserRequestDTO;
import prefolio.prefolioserver.dto.request.UserInfoRequestDTO;
import prefolio.prefolioserver.dto.response.GetUserInfoResponseDTO;
import prefolio.prefolioserver.dto.response.CheckUserResponseDTO;
import prefolio.prefolioserver.dto.response.UserInfoResponseDTO;
import prefolio.prefolioserver.error.CustomException;
import prefolio.prefolioserver.repository.UserRepository;
import prefolio.prefolioserver.repository.ScrapRepository;
import prefolio.prefolioserver.repository.LikeRepository;

import java.util.Date;
import java.util.Optional;

import static prefolio.prefolioserver.error.ErrorCode.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final LikeRepository likeRepository;


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

        Optional<User> user = userRepository.findByNickname(checkUserRequest.getNickname());
        if (user.isEmpty()) {
            return new CheckUserResponseDTO(false);
        }
        return new CheckUserResponseDTO(true);
    }

    public UserInfoResponseDTO getUserId(UserDetailsImpl authUser) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return new UserInfoResponseDTO(user.getId());
    }

    public GetUserInfoResponseDTO getUserInfo(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Optional<Long> countScrap = scrapRepository.countByUserId(userId);
        Optional<Long> countLike = likeRepository.countByUserId(userId);
        //조건문 추가
        if(countScrap.isEmpty() && countLike.isEmpty())
            return new GetUserInfoResponseDTO(user,0L,0L);

    return new GetUserInfoResponseDTO(user, countScrap.get(), countLike.get());
    }
}
