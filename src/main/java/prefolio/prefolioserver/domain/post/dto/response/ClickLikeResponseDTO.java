package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ClickLikeResponseDTO {

    private Long likes;
    private Boolean isLiked;

    public ClickLikeResponseDTO(Long likes, Boolean isLiked) {
        this.likes = likes;
        this.isLiked = isLiked;
    }
}
