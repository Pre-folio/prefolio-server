package prefolio.prefolioserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.User;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckUserRequestDTO {


        private String nickname;


        public CheckUserRequestDTO (User user) {
            this.nickname = user.getNickname();
        }
}
