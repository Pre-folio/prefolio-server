package prefolio.prefolioserver.domain.post.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequestDTO {

    private String refreshToken;

    @Builder
    private TokenRequestDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static TokenRequestDTO from(String refreshToken) {
        return TokenRequestDTO.builder()
                .refreshToken(refreshToken)
                .build();
    }
}
