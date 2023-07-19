package prefolio.prefolioserver.domain.post.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.domain.Post;

@Getter
@NoArgsConstructor
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

    @Builder
    private AddPostRequestDTO(String thumbnail, String title, String startDate, String endDate, Integer contribution, String task, String tools, String partTag, String actTag, String contents) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contribution = contribution;
        this.task = task;
        this.tools = tools;
        this.partTag = partTag;
        this.actTag = actTag;
        this.contents = contents;
    }

    public static AddPostRequestDTO from(Post post) {
        return AddPostRequestDTO.builder()
                .thumbnail(post.getThumbnail())
                .title(post.getTitle())
                .startDate(post.getStartDate())
                .endDate(post.getEndDate())
                .contribution(post.getContribution())
                .task(post.getTask())
                .tools(post.getTools())
                .partTag(post.getPartTag())
                .actTag(post.getActTag())
                .contents(post.getContents())
                .build();
    }
}
