package prefolio.prefolioserver.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.Post;

public class AddPostDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String thumbnail;
        private String title;
        private String startDate;
        private String endDate;
        private Integer contribution;
        private String tools;
        private String partTag;
        private String actTag;
        private String contents;

        /* Dto -> Entity */
        public Request (Post post) {
            this.thumbnail = post.getThumbnail();
            this.title = post.getTitle();
            this.startDate = post.getStartDate();
            this.endDate = post.getEndDate();
            this.contribution = post.getContribution();
            this.tools = post.getTools();
            this.partTag = post.getPartTag();
            this.actTag = post.getActTag();
            this.contents = post.getContents();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

//        @Column(name = "id")
        private Long postId;

        /* Entity -> Dto */
        public Response(Post post) {
            this.postId = post.getId();
        }
    }

}
