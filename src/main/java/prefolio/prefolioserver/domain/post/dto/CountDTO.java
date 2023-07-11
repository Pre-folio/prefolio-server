package prefolio.prefolioserver.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class CountDTO {

    private int likes;
    private int scraps;

    public CountDTO(int likes, int scraps) {
        this.likes = likes;
        this.scraps = scraps;
    }

}
