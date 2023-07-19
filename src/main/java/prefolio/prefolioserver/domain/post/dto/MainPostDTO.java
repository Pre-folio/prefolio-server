package prefolio.prefolioserver.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import prefolio.prefolioserver.domain.post.domain.Post;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class MainPostDTO {

    private Long id;
    private String thumbnail;
    private String title;
    private List<String> partTag;
    private List<String> actTag;
    private Integer hits;
    private Boolean isScrapped;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date createdAt;
    private Boolean isMine;

    @Builder
    private MainPostDTO(Long id, String thumbnail, String title, List<String> partTag, List<String> actTag, Integer hits, Boolean isScrapped, Date createdAt, Boolean isMine) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.partTag = partTag;
        this.actTag = actTag;
        this.hits = hits;
        this.isScrapped = isScrapped;
        this.createdAt = createdAt;
        this.isMine = isMine;
    }

    public static MainPostDTO of(Post post, List<String> partTag, List<String> actTag, Boolean isScrapped, Boolean isMine) {
        return MainPostDTO.builder()
                .id(post.getId())
                .thumbnail(post.getThumbnail())
                .title(post.getTitle())
                .partTag(partTag)
                .actTag(actTag)
                .hits(post.getHits())
                .isScrapped(isScrapped)
                .isMine(isMine)
                .build();
    }
}
