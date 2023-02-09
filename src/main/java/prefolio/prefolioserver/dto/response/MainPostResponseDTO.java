package prefolio.prefolioserver.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.dto.MainPostDTO;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class MainPostResponseDTO {

    private Long userId;
    private List<MainPostDTO> posts;
    private Integer totalPages;
    private Long totalResults;

    public MainPostResponseDTO(Long userId, List<MainPostDTO> posts, Integer totalPages, Long totalResults) {
        this.userId = userId;
        this.posts = posts;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }
}
