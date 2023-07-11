package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.dto.CommentDTO;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class CommentResponseDTO {

    private List<CommentDTO> comments;
    private Integer totalPages;
    private Long totalResults;

    public CommentResponseDTO(List<CommentDTO> comments, Integer totalPages, Long totalResults) {
        this.comments = comments;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }
}
