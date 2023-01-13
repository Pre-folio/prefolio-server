package prefolio.prefolioserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.User;

public class GetUserInfoDTO {

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

        private Long countScrap;

        private Long countLike;


        /* Dto -> Entity */
        public Request (User user, Long countScrap, Long countLike) {
            this.type = user.getType();
            this.nickname = user.getNickname();
            this.profileImage = user.getProfileImage();
            this.grade = user.getGrade();
            this.refreshToken = user.getRefreshToken();
            this.countScrap = countScrap;
            this.countLike = countLike;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long userId;
        private String type;
        private String nickname;
        private String profileImage;
        private Integer grade;
        private String refreshToken;

        private Long countScrap;

        private Long countLike;


        /* Entity -> Dto */
        public Response(User user, Long countScrap, Long countLike) {
            this.userId = user.getId();
            this.type = user.getType();
            this.nickname = user.getNickname();
            this.profileImage = user.getProfileImage();
            this.grade = user.getGrade();
            this.refreshToken = user.getRefreshToken();
            this.countScrap = countScrap;
            this.countLike = countLike;
        }
    }
}