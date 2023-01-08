package prefolio.prefolioserver.dto;

import lombok.Getter;
import prefolio.prefolioserver.domain.Post;

import java.sql.Date;

@Getter
public class MainPostDTO {

    private Long id;
    private String thumbnail;
    private String title;
    private String part_tag;
    private String act_tag;
    private Integer hits;
    private Date created_at;

    public MainPostDTO(Post post) {
        this.id = post.getId();
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.part_tag = post.getPart_tag();
        this.act_tag = post.getAct_tag();
        this.hits = post.getHits();
        this.created_at = post.getCreated_at();
    }
}
