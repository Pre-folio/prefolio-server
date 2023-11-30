package prefolio.prefolioserver.domain.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CheckUserRequestDTO {

    private Long userId;
    private String nickname;

    @Builder
    public CheckUserRequestDTO(String nickname) {
        this.nickname = nickname;
    }
}
