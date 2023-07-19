package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.dto.CountDTO;
import prefolio.prefolioserver.domain.post.dto.TagListPostDTO;
import prefolio.prefolioserver.domain.user.dto.UserDTO;

@Getter
@NoArgsConstructor
public class GetPostResponseDTO {

    private TagListPostDTO post;
    private CountDTO count;
    private UserDTO user;
    private Boolean isLiked;
    private Boolean isScrapped;

    @Builder
    private GetPostResponseDTO(TagListPostDTO post, CountDTO count, UserDTO user, Boolean isLiked, Boolean isScrapped) {
        this.post = post;
        this.count = count;
        this.user = user;
        this.isLiked = isLiked;
        this.isScrapped = isScrapped;
    }

    public static GetPostResponseDTO of(TagListPostDTO post, CountDTO count, UserDTO user, Boolean isLiked, Boolean isScrapped) {
        return GetPostResponseDTO.builder()
                .post(post)
                .count(count)
                .user(user)
                .isLiked(isLiked)
                .isScrapped(isScrapped)
                .build();
    }
}
