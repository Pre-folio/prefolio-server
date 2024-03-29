package prefolio.prefolioserver.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.domain.Post;
import prefolio.prefolioserver.domain.post.domain.Scrap;

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
    private Boolean isScrapped;
    private Boolean isMine;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date createdAt;

    public CardPostDTO(Scrap scrap, List<String> partTag, List<String> actTag,  Boolean isScrapped) {
        this.postId = scrap.getPost().getId();
        this.thumbnail = scrap.getPost().getThumbnail();
        this.title = scrap.getPost().getTitle();
        this.partTag = partTag;
        this.actTag = actTag;
        this.hits = scrap.getPost().getHits();
        this.isScrapped = isScrapped;
        this.createdAt = scrap.getPost().getCreatedAt();
    }

    public CardPostDTO(Post post, List<String> partTag, List<String> actTag,  Boolean isScrapped, Boolean isMine) {
        this.postId = post.getId();
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.partTag = partTag;
        this.actTag = actTag;
        this.hits = post.getHits();
        this.isScrapped = isScrapped;
        this.createdAt = post.getCreatedAt();
        this.isMine = isMine;
    }

}
