package prefolio.prefolioserver.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.domain.Post;
import prefolio.prefolioserver.domain.post.domain.Scrap;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
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

    @Builder
    private CardPostDTO(Long postId, String thumbnail, String title, List<String> partTag, List<String> actTag, Integer hits, Boolean isScrapped, Boolean isMine, Date createdAt) {
        this.postId = postId;
        this.thumbnail = thumbnail;
        this.title = title;
        this.partTag = partTag;
        this.actTag = actTag;
        this.hits = hits;
        this.isScrapped = isScrapped;
        this.isMine = isMine;
        this.createdAt = createdAt;
    }

    public static CardPostDTO scrapToDto(Scrap scrap, List<String> partTag, List<String> actTag, Boolean isScrapped) {
        return CardPostDTO.builder()
                .postId(scrap.getPost().getId())
                .thumbnail(scrap.getPost().getThumbnail())
                .title(scrap.getPost().getTitle())
                .partTag(partTag)
                .actTag(actTag)
                .hits(scrap.getPost().getHits())
                .isScrapped(isScrapped)
                .createdAt(scrap.getPost().getCreatedAt())
                .build();
    }

    public static CardPostDTO postToDto(Post post, List<String> partTag, List<String> actTag,  Boolean isScrapped, Boolean isMine) {
        return CardPostDTO.builder()
                .postId(post.getId())
                .thumbnail(post.getThumbnail())
                .title(post.getTitle())
                .partTag(partTag)
                .actTag(actTag)
                .hits(post.getHits())
                .isScrapped(isScrapped)
                .createdAt(post.getCreatedAt())
                .isMine(isMine)
                .build();
    }
}
