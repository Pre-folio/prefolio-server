package prefolio.prefolioserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.User;

public class ClickScrapDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    public static class Request {
        private Boolean isScrapped;
        private User user;

        public Request(Boolean isScrapped, User user) {
            this.isScrapped = isScrapped;
            this.user = user;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    public static class Response {
        private Long scraps;
        private Boolean isScrapped;

        public Response(Long scraps, Boolean isScrapped) {
            this.scraps = scraps;
            this.isScrapped = isScrapped;
        }
    }
}
