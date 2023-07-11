package prefolio.prefolioserver.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class CheckUserResponseDTO {


    private Boolean is_used;

    public CheckUserResponseDTO(Boolean is_used) {this.is_used = is_used;}

}
