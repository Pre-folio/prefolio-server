package prefolio.prefolioserver.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.domain.Post;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class TagListPostDTO {
    private Long id;
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

    @Builder
    private TagListPostDTO(Long id, String thumbnail, String title, String startDate, String endDate, Integer contribution, String task, List<String> tools, List<String> partTag, List<String> actTag, String contents, Integer hits, Date createdAt) {
        this.id = id;
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
        this.hits = hits;
        this.createdAt = createdAt;
    }

    public static TagListPostDTO of(Post post, List<String> tools, List<String> partTag, List<String>actTag) {
        return TagListPostDTO.builder()
                .id(post.getId())
                .thumbnail(post.getThumbnail())
                .title(post.getTitle())
                .startDate(post.getStartDate())
                .endDate(post.getEndDate())
                .contribution(post.getContribution())
                .task(post.getTask())
                .tools(tools)
                .partTag(partTag)
                .actTag(actTag)
                .contents(post.getContents())
                .hits(post.getHits())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
