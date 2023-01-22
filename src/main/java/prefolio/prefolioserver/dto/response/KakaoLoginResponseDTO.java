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
    private Boolean isMember;

    public KakaoLoginResponseDTO(Long userId, String accessToken, Boolean isMember) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.isMember = isMember;
    }
}
