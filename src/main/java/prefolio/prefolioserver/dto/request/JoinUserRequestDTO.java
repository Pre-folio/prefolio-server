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
public class JoinUserRequestDTO {

        private String type;

        private String nickname;

        private String profileImage;

        private Integer grade;


        /* Dto -> Entity */
        public JoinUserRequestDTO (User user) {
            this.type = user.getType();
            this.nickname = user.getNickname();
            this.profileImage = user.getProfileImage();
            this.grade = user.getGrade();
        }
}