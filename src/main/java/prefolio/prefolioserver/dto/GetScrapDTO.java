package prefolio.prefolioserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.Post;
import prefolio.prefolioserver.domain.Scrap;
import prefolio.prefolioserver.domain.User;

public class GetScrapDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor

    public static class Request {

        private Long scrapId;

        private Post post;

        /* Dto -> Entity */
        public Request(Scrap scrap) {
            this.scrapId = scrap.getId();
            this.post = scrap.getPost();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long scrapId;
        private Post post;


        /* Entity -> Dto */
        public Response(Scrap scrap) {
            this.scrapId = scrap.getId();
            this.post = scrap.getPost();
        }
    }
}
