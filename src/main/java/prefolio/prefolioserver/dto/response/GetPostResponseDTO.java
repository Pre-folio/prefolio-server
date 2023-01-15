package prefolio.prefolioserver.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.User;
import prefolio.prefolioserver.dto.CountDTO;
import prefolio.prefolioserver.dto.PostDTO;

@Getter
@Builder
@NoArgsConstructor
public class GetPostResponseDTO {

    private PostDTO post;
    private CountDTO count;
    private User user;
    private Boolean isLiked;
    private Boolean isScrapped;

    public GetPostResponseDTO(PostDTO post, CountDTO count, User user, Boolean isLiked, Boolean isScrapped) {
        this.post = post;
        this.count = count;
        this.user = user;
        this.isLiked = isLiked;
        this.isScrapped = isScrapped;
    }
}
