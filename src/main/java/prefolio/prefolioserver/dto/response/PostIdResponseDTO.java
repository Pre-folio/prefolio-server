package prefolio.prefolioserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.Post;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostIdResponseDTO {

    private Long postId;

    public PostIdResponseDTO(Post post) {
        this.postId = post.getId();
    }
}
