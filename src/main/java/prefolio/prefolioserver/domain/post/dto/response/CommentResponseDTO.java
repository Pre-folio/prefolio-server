package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.dto.CommentDTO;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponseDTO {

    private List<CommentDTO> comments;
    private Integer totalPages;
    private Long totalResults;

    @Builder
    private CommentResponseDTO(List<CommentDTO> comments, Integer totalPages, Long totalResults) {
        this.comments = comments;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public static CommentResponseDTO of(List<CommentDTO> comments, Integer totalPages, Long totalResults) {
        return CommentResponseDTO.builder()
                .comments(comments)
                .totalPages(totalPages)
                .totalResults(totalResults)
                .build();
    }
}
