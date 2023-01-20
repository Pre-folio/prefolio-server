package prefolio.prefolioserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.Post;
import prefolio.prefolioserver.domain.Scrap;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardPostDTO {

    private Long postId;
    private String thumbnail;
    private String title;
    private List<String> partTag;
    private List<String> actTag;

    public CardPostDTO(Scrap scrap, List<String> partTag, List<String> actTag) {
        this.postId = scrap.getPost().getId();
        this.thumbnail = scrap.getPost().getThumbnail();
        this.title = scrap.getPost().getTitle();
        this.partTag = partTag;
        this.actTag = actTag;
    }

    public CardPostDTO(Post post, List<String> partTag, List<String> actTag) {
        this.postId = post.getId();
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.partTag = partTag;
        this.actTag = actTag;
    }

}
