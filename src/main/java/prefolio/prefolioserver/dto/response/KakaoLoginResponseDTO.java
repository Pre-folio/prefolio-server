package prefolio.prefolioserver.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class KakaoLoginResponseDTO {

    private Long userId;
    private String accessToken;
    private String refreshToken;
    private Boolean isMember;

    public KakaoLoginResponseDTO(Long userId, String accessToken, String refreshToken, Boolean isMember) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isMember = isMember;
    }
}
