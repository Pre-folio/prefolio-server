package prefolio.prefolioserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.User;

public class CheckUserDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private String nickname;


        /* Dto -> Entity */
        public Request (User user) {
            this.nickname = user.getNickname();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    public static class Response {

        private Boolean is_used;

        /* Entity -> Dto */
        public Response(Boolean is_used) {this.is_used = is_used;}
    }
}
