package prefolio.prefolioserver.dto.response;

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
public class GetScrapResponseDTO {
    private Long scrapId;
    private Post post;


    /* Entity -> Dto */
    public GetScrapResponseDTO(Scrap scrap) {
        this.scrapId = scrap.getId();
        this.post = scrap.getPost();
    }
}
