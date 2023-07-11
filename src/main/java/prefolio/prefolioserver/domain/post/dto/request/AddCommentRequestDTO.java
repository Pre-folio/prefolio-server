package prefolio.prefolioserver.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.domain.Comment;
import prefolio.prefolioserver.domain.post.domain.Post;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentRequestDTO {

    private Post post;
    private String contents;

    public AddCommentRequestDTO(Comment comment) {
        this.post = comment.getPost();
        this.contents = comment.getContents();
    }
}
