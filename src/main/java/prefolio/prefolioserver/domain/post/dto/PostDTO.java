package prefolio.prefolioserver.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.domain.Post;
import prefolio.prefolioserver.domain.user.domain.User;

import java.util.Date;

@Getter
@NoArgsConstructor
public class PostDTO {

    private Long id;
    private User user;
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
    private Integer hits;
    private Integer likes;
    private Integer scraps;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date createdAt;

    @Builder
    private PostDTO(Long id, User user, String thumbnail, String title, String startDate, String endDate, Integer contribution, String task, String tools, String partTag, String actTag, String contents, Integer hits, Integer likes, Integer scraps, Date createdAt) {
        this.id = id;
        this.user = user;
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
        this.likes = likes;
        this.scraps = scraps;
        this.createdAt = createdAt;
    }

    public static PostDTO entityToDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .user(post.getUser())
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
                .hits(post.getHits())
                .likes(post.getLikes())
                .scraps(post.getScraps())
                .createdAt(post.getCreatedAt())
                .build();
    }
}