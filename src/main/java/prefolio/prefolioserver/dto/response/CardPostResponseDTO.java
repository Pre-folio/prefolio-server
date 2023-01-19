package prefolio.prefolioserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.Scrap;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardPostResponseDTO {

    private Long postId;

    private String thumbnail;

    private String title;

    private List<String> partTag;

    private List<String> actTag;


    public CardPostResponseDTO(Scrap scrap, List<String> partTag, List<String> actTag) {
        this.postId = scrap.getPost().getId();
        this.thumbnail = scrap.getPost().getThumbnail();
        this.title = scrap.getPost().getTitle();
        this.partTag = partTag;
        this.actTag = actTag;
    }
}
