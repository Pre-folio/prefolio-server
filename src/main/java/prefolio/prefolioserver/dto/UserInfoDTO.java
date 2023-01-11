package prefolio.prefolioserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.User;

public class UserInfoDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private String type;

        private String nickname;

        private String profileImage;

        private Integer grade;

        private String refreshToken;

        /* Dto -> Entity */
        public Request (User user) {
            this.type = user.getType();
            this.nickname = user.getNickname();
            this.profileImage = user.getProfileImage();
            this.grade = user.getGrade();
            this.refreshToken = user.getRefreshToken();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long userId;

        /* Entity -> Dto */
        public Response(User user) {this.userId = user.getId();}
    }
}
