package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.domain.Post;

@Getter
@NoArgsConstructor
public class PostIdResponseDTO {

    private Long postId;

    @Builder
    private PostIdResponseDTO(Long postId) {
        this.postId = postId;
    }

    public static PostIdResponseDTO from(Post post) {
        return PostIdResponseDTO.builder()
                .postId(post.getId())
                .build();
    }
}
