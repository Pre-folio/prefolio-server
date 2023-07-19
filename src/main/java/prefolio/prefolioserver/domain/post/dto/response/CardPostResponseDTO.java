package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.dto.CardPostDTO;

import java.util.List;

@Getter
@NoArgsConstructor
public class CardPostResponseDTO {

    private List<CardPostDTO> cardPosts;

    private Integer totalPages;

    private Long totalResults;


    @Builder
    private CardPostResponseDTO(List<CardPostDTO> cardPosts, Integer totalPages, Long totalResults) {
        this.cardPosts = cardPosts;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public static CardPostResponseDTO of(List<CardPostDTO> cardPosts, Integer totalPages, Long totalResults) {
        return CardPostResponseDTO.builder()
                .cardPosts(cardPosts)
                .totalPages(totalPages)
                .totalResults(totalResults)
                .build();
    }
}
