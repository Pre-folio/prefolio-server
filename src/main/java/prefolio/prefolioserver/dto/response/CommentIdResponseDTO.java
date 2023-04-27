package prefolio.prefolioserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.Comment;
import prefolio.prefolioserver.domain.Post;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentIdResponseDTO {

    private Long commentId;

    public CommentIdResponseDTO(Comment comment) {
        this.commentId = comment.getId();
    }
}
