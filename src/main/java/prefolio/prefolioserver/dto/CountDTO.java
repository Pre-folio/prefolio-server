package prefolio.prefolioserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class CountDTO {

    private int hits;
    private int likes;
    private int scraps;

    public CountDTO(int hits, int likes, int scraps) {
        this.hits = hits;
        this.likes = likes;
        this.scraps = scraps;
    }

}
