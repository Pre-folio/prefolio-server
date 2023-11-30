package prefolio.prefolioserver.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserIdResponseDTO {
        private Long userId;

        @Builder
        public UserIdResponseDTO(Long userId) {this.userId = userId;}

        public static UserIdResponseDTO from(Long userId){
                return UserIdResponseDTO.builder()
                        .userId(userId)
                        .build();
        }
}
