package prefolio.prefolioserver.domain.post.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.domain.Post;
import prefolio.prefolioserver.domain.post.domain.Scrap;

@Getter
@NoArgsConstructor
public class GetScrapRequestDTO {

    private Long scrapId;
    private Post post;

    @Builder
    private GetScrapRequestDTO(Long scrapId, Post post) {
        this.scrapId = scrapId;
        this.post = post;
    }

    public static GetScrapRequestDTO from(Scrap scrap) {
        return GetScrapRequestDTO.builder()
                .scrapId(scrap.getId())
                .post(scrap.getPost())
                .build();
    }
}
