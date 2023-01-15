package prefolio.prefolioserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.Post;
import prefolio.prefolioserver.domain.Scrap;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetScrapRequestDTO {

    private Long scrapId;

    private Post post;

    /* Dto -> Entity */
    public GetScrapRequestDTO(Scrap scrap) {
        this.scrapId = scrap.getId();
        this.post = scrap.getPost();
    }
}
