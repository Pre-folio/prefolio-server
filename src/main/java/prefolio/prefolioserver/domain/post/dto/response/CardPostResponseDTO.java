package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.dto.CardPostDTO;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class CardPostResponseDTO {

    private List<CardPostDTO> cardPosts;

    private Integer totalPages;

    private Long totalResults;


    public CardPostResponseDTO(List<CardPostDTO> cardPosts, Integer totalPages, Long totalResults) {
        this.cardPosts = cardPosts;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }
}
