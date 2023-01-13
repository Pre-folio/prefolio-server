package prefolio.prefolioserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class KakaoLoginDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    public static class Response {
        private String token;

        public Response(String token) {
            this.token = token;
        }
    }
}
