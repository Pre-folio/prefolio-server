package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.dto.MainPostDTO;

import java.util.List;

@Getter
@NoArgsConstructor
public class MainPostResponseDTO {

    private List<MainPostDTO> posts;
    private Integer totalPages;
    private Long totalResults;

    @Builder
    private MainPostResponseDTO(List<MainPostDTO> posts, Integer totalPages, Long totalResults) {
        this.posts = posts;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public static MainPostResponseDTO of(List<MainPostDTO> posts, Integer totalPages, Long totalResults) {
        return MainPostResponseDTO.builder()
                .posts(posts)
                .totalPages(totalPages)
                .totalResults(totalResults)
                .build();
    }
}
