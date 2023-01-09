package prefolio.prefolioserver.dto;

import lombok.Getter;
import prefolio.prefolioserver.domain.Post;

import java.sql.Date;

@Getter
public class PostDTO {

    private Long id;
    private String thumbnail;
    private String title;
    private PartTag partTag;
    private ActTag actTag;
    private Integer hits;
    private Date createdAt;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.partTag = post.getPartTag();
        this.actTag = post.getActTag();
        this.hits = post.getHits();
        this.createdAt = post.getCreatedAt();
    }
}