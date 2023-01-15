package prefolio.prefolioserver.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.User;

@Getter
@Builder
@NoArgsConstructor
public class ClickScrapRequestDTO {

    private Boolean isScrapped;
    private User user;

    public ClickScrapRequestDTO(Boolean isScrapped, User user) {
        this.isScrapped = isScrapped;
        this.user = user;
    }
}
