package prefolio.prefolioserver.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.domain.Post;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddPostRequestDTO {

    private String thumbnail;
    private String title;
    private String startDate;
    private String endDate;
    private Integer contribution;
    private String task;
    private String tools;
    private String partTag;
    private String actTag;
    private String contents;

    public AddPostRequestDTO (Post post) {
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.startDate = post.getStartDate();
        this.endDate = post.getEndDate();
        this.contribution = post.getContribution();
        this.task = post.getTask();
        this.tools = post.getTools();
        this.partTag = post.getPartTag();
        this.actTag = post.getActTag();
        this.contents = post.getContents();
    }
}
