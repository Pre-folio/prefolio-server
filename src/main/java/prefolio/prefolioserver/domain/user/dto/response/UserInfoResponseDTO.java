package prefolio.prefolioserver.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.user.domain.User;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDTO {
        private Long userId;

        public UserInfoResponseDTO(User user) {this.userId = user.getId();}
}
