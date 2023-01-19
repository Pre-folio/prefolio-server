package prefolio.prefolioserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.Scrap;
import prefolio.prefolioserver.domain.User;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardPostRequestDTO {

    private Long userId;

    /* Dto -> Entity */
    public CardPostRequestDTO(User user) {
        this.userId = user.getId();
    }
}
