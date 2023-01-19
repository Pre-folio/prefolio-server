package prefolio.prefolioserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import prefolio.prefolioserver.domain.Post;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private String thumbnail;
    private String title;
    private String startDate;
    private String endDate;
    private Integer contribution;
    private String tools;
    private String partTag;
    private String actTag;
    private Integer hits;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date createdAt;

    public PostDTO(Post post) {
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