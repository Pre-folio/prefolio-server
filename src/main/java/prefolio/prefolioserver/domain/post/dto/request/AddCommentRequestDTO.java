package prefolio.prefolioserver.domain.post.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.domain.Comment;
import prefolio.prefolioserver.domain.post.domain.Post;

@Getter
@NoArgsConstructor
public class AddCommentRequestDTO {

    private Post post;
    private String contents;

    @Builder
    private AddCommentRequestDTO(Post post, String contents) {
        this.post = post;
        this.contents = contents;
    }

    public static AddCommentRequestDTO from(Comment comment) {
        return AddCommentRequestDTO.builder()
                .post(comment.getPost())
                .contents(comment.getContents())
                .build();
    }
}
