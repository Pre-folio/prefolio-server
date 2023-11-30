package prefolio.prefolioserver.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CheckUserResponseDTO {


    private Boolean isUsed;

    @Builder
    public CheckUserResponseDTO(Boolean isUsed) {this.isUsed = isUsed;}

    public static CheckUserResponseDTO from(Boolean isUsed) {
        return CheckUserResponseDTO.builder()
                .isUsed(isUsed)
                .build();
    }
}
