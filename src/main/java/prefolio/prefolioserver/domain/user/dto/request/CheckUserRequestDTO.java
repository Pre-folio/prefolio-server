package prefolio.prefolioserver.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.user.domain.User;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckUserRequestDTO {

    private Long userId;
    private String nickname;


    public CheckUserRequestDTO(User user) {
        this.nickname = user.getNickname();
    }
}
