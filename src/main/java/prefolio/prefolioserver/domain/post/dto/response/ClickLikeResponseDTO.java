package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClickLikeResponseDTO {

    private Long likes;
    private Boolean isLiked;

    @Builder
    private ClickLikeResponseDTO(Long likes, Boolean isLiked) {
        this.likes = likes;
        this.isLiked = isLiked;
    }

    public static ClickLikeResponseDTO of(Long likes, Boolean isLiked) {
        return ClickLikeResponseDTO.builder()
                .likes(likes)
                .isLiked(isLiked)
                .build();
    }
}
