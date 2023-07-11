package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.dto.CountDTO;
import prefolio.prefolioserver.domain.post.dto.PostDTO;
import prefolio.prefolioserver.domain.user.dto.UserDTO;

@Getter
@Builder
@NoArgsConstructor
public class GetPostResponseDTO {

    private PostDTO post;
    private CountDTO count;
    private UserDTO user;
    private Boolean isLiked;
    private Boolean isScrapped;

    public GetPostResponseDTO(PostDTO post, CountDTO count, UserDTO user, Boolean isLiked, Boolean isScrapped) {
        this.post = post;
        this.count = count;
        this.user = user;
        this.isLiked = isLiked;
        this.isScrapped = isScrapped;
    }
}
