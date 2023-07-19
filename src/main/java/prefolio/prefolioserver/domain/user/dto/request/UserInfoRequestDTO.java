package prefolio.prefolioserver.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.user.domain.Type;
import prefolio.prefolioserver.domain.user.domain.User;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequestDTO {

        private Type type;

        private String nickname;

        private String profileImage;

        private Integer grade;


        /* Dto -> Entity */
        public UserInfoRequestDTO (User user) {
            this.type = user.getType();
            this.nickname = user.getNickname();
            this.profileImage = user.getProfileImage();
            this.grade = user.getGrade();
        }
}