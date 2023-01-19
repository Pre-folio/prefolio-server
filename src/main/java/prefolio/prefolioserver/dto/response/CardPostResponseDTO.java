package prefolio.prefolioserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.Scrap;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardPostResponseDTO {

    private Long postId;

    private String thumbnail;

    private String title;

    private String partTag;

    private String actTag;


    public CardPostResponseDTO(Scrap scrap) {
        this.postId = scrap.getPost().getId();
        this.thumbnail = scrap.getPost().getThumbnail();
        this.title = scrap.getPost().getTitle();
        this.partTag = scrap.getPost().getPartTag();
        this.actTag = scrap.getPost().getActTag();
    }
}
