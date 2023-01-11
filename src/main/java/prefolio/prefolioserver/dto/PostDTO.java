package prefolio.prefolioserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.Post;

import java.sql.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long id;
    private String thumbnail;
    private String title;
    private String startDate;
    private String endDate;
    private Integer contribution;
    private String tools;
    private String partTag;
    private String actTag;
    private Integer hits;
    private Date createdAt;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.startDate = post.getStartDate();
        this.endDate = post.getEndDate();
        this.contribution = post.getContribution();
        this.tools = post.getTools();
        this.partTag = post.getPartTag();
        this.actTag = post.getActTag();
        this.hits = post.getHits();
        this.createdAt = post.getCreatedAt();
    }
}