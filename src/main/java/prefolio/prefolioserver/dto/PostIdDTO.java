package prefolio.prefolioserver.dto;

import lombok.Getter;
import prefolio.prefolioserver.domain.Post;

import java.sql.Date;

@Getter
public class PostIdDTO {

    private Long id;

    public PostIdDTO(Post post) {
        this.id = post.getId();
    }
}