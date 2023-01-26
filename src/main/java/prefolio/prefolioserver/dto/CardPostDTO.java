package prefolio.prefolioserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.Post;
import prefolio.prefolioserver.domain.Scrap;

import java.util.Date;
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
    private Integer hits;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date createdAt;

    public CardPostDTO(Scrap scrap, List<String> partTag, List<String> actTag) {
        this.postId = scrap.getPost().getId();
        this.thumbnail = scrap.getPost().getThumbnail();
        this.title = scrap.getPost().getTitle();
        this.partTag = partTag;
        this.actTag = actTag;
        this.hits = scrap.getPost().getHits();
        this.createdAt = scrap.getPost().getCreatedAt();
    }

    public CardPostDTO(Post post, List<String> partTag, List<String> actTag) {
        this.postId = post.getId();
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.partTag = partTag;
        this.actTag = actTag;
        this.hits = post.getHits();
        this.createdAt = post.getCreatedAt();
    }

}
