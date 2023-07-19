package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.domain.Comment;

@Getter
@NoArgsConstructor
public class CommentIdResponseDTO {

    private Long commentId;

    @Builder
    private CommentIdResponseDTO(Long commentId) {
        this.commentId = commentId;
    }
    public static CommentIdResponseDTO from(Comment comment) {
        return CommentIdResponseDTO.builder()
                .commentId(comment.getId())
                .build();
    }
}
