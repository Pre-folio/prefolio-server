package prefolio.prefolioserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.User;

@Getter
@Builder
@NoArgsConstructor
public class TokenResponseDTO {
    private String accessToken;


    public TokenResponseDTO(String accessToken) {this.accessToken = accessToken;}
}
