package prefolio.prefolioserver.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class TokenResponseDTO {
    private String accessToken;


    public TokenResponseDTO(String accessToken) {this.accessToken = accessToken;}
}
