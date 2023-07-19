package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;

@Getter
@NoArgsConstructor
public class GetPathResponseDTO {

    private URL url;

    @Builder
    private GetPathResponseDTO(URL url) {this.url = url;}

    public static GetPathResponseDTO from(URL url) {
        return GetPathResponseDTO.builder()
                .url(url)
                .build();
    }
}
