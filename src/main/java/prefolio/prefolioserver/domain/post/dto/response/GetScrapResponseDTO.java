package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.domain.Post;
import prefolio.prefolioserver.domain.post.domain.Scrap;

@Getter
@NoArgsConstructor
public class GetScrapResponseDTO {
    private Long scrapId;
    private Post post;

    @Builder
    private GetScrapResponseDTO(Long scrapId, Post post) {
        this.scrapId = scrapId;
        this.post = post;
    }

    public static GetScrapResponseDTO from(Scrap scrap) {
        return GetScrapResponseDTO.builder()
                .scrapId(scrap.getId())
                .post(scrap.getPost())
                .build();
    }
}
