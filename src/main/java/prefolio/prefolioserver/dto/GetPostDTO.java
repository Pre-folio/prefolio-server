package prefolio.prefolioserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.User;

public class GetPostDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    public static class Response {

        private PostDTO post;
        private CountDTO count;
        private User user;
        private Boolean isLiked;
        private Boolean isScrapped;

        public Response(PostDTO post, CountDTO count, User user, Boolean isLiked, Boolean isScrapped) {
            this.post = post;
            this.count = count;
            this.user = user;
            this.isLiked = isLiked;
            this.isScrapped = isScrapped;
        }
    }
}
