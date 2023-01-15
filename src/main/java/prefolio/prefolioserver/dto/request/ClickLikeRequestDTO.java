package prefolio.prefolioserver.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.User;

@Getter
@Builder
@NoArgsConstructor
public class ClickLikeRequestDTO {

    private Boolean isLiked;
    private User user;

    public ClickLikeRequestDTO(Boolean isLiked, User user) {
        this.isLiked = isLiked;
        this.user = user;
    }
}
