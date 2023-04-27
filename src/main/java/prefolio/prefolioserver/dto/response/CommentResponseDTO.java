package prefolio.prefolioserver.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.dto.CommentDTO;
import prefolio.prefolioserver.dto.MainPostDTO;

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
