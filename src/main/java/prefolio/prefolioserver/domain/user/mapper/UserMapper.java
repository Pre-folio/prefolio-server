package prefolio.prefolioserver.domain.user.mapper;

import org.springframework.stereotype.Component;
import prefolio.prefolioserver.domain.user.domain.User;
import prefolio.prefolioserver.domain.user.dto.response.CheckUserResponseDTO;
import prefolio.prefolioserver.domain.user.dto.response.GetUserInfoResponseDTO;
import prefolio.prefolioserver.domain.user.dto.response.UserIdResponseDTO;

@Component
public class UserMapper {

    public CheckUserResponseDTO toCheckUserResponse(boolean isUsed) {
        return CheckUserResponseDTO.from(isUsed);
    }

    public UserIdResponseDTO toUserIdResponse(Long userId) {
        return UserIdResponseDTO.from(userId);
    }

    public GetUserInfoResponseDTO toGetUserInfoResponse(User user, Long countLike, Long countScrap) {
        return GetUserInfoResponseDTO.of(user, countLike, countScrap);
    }
}
