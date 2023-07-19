package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClickScrapResponseDTO {

    private Long scraps;
    private Boolean isScrapped;

    @Builder
    private ClickScrapResponseDTO(Long scraps, Boolean isScrapped) {
        this.scraps = scraps;
        this.isScrapped = isScrapped;
    }

    public static ClickScrapResponseDTO of(Long scraps, Boolean isScrapped) {
        return ClickScrapResponseDTO.builder()
                .scraps(scraps)
                .isScrapped(isScrapped)
                .build();
    }
}
