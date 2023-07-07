package prefolio.prefolioserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;

@Getter
@Builder
@NoArgsConstructor
public class GetPathResponseDTO {

    private URL url;


    /* Entity -> Dto */
    public GetPathResponseDTO(URL url) {this.url = url;}
}
