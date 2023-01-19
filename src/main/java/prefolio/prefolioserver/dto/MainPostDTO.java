package prefolio.prefolioserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import prefolio.prefolioserver.domain.Post;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainPostDTO {

    private Long id;
    private String thumbnail;
    private String title;
    private List<String> partTag;
    private List<String> actTag;
    private Integer hits;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date createdAt;

    public MainPostDTO(Post post, List<String> partTag, List<String> actTag) {
        this.id = post.getId();
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.partTag = partTag;
        this.actTag = actTag;
        this.hits = post.getHits();
        this.createdAt = post.getCreatedAt();
    }
}
