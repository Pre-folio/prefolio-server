package prefolio.prefolioserver.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CountDTO {

    private int likes;
    private int scraps;

    @Builder
    private CountDTO(int likes, int scraps) {
        this.likes = likes;
        this.scraps = scraps;
    }

    public static CountDTO of(int likes, int scraps) {
        return CountDTO.builder()
                .likes(likes)
                .scraps(scraps)
                .build();
    }
}
