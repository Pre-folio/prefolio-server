package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ClickScrapResponseDTO {

    private Long scraps;
    private Boolean isScrapped;

    public ClickScrapResponseDTO(Long scraps, Boolean isScrapped) {
        this.scraps = scraps;
        this.isScrapped = isScrapped;
    }
}
