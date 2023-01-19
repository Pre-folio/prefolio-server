package prefolio.prefolioserver.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class KakaoLoginResponseDTO {

    private String accessToken;

    public KakaoLoginResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
