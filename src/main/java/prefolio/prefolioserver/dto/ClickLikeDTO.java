package prefolio.prefolioserver.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.User;

public class ClickLikeDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    public static class Request {
        private Boolean isLiked;
        private User user;

        public Request(Boolean isLiked, User user) {
            this.isLiked = isLiked;
            this.user = user;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    public static class Response {

        private Long likes;
        private Boolean isLiked;

        public Response(Long likes, Boolean isLiked) {
            this.likes = likes;
            this.isLiked = isLiked;
        }
    }
}
