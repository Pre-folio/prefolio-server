package prefolio.prefolioserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import prefolio.prefolioserver.domain.Post;

import java.util.Date;
import java.util.List;

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
    private String task;
    private List<String> tools;
    private List<String> partTag;
    private List<String> actTag;
    private String contents;
    private Integer hits;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date createdAt;

    public PostDTO(Post post, List<String> tools, List<String> partTag, List<String>actTag) {
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.startDate = post.getStartDate();
        this.endDate = post.getEndDate();
        this.contribution = post.getContribution();
        this.task = post.getTask();
        this.tools = tools;
        this.partTag = partTag;
        this.actTag = actTag;
        this.contents = post.getContents();
        this.hits = post.getHits();
        this.createdAt = post.getCreatedAt();
    }
}