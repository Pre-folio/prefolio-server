package prefolio.prefolioserver.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import prefolio.prefolioserver.domain.user.domain.Type;
import prefolio.prefolioserver.domain.user.domain.User;

@Getter
public class GetUserInfoResponseDTO {

    private Long userId;
    private Type type;
    private String nickname;
    private String profileImage;
    private Integer grade;
    private Long countLike;
    private Long countScrap;

    @Builder
    public GetUserInfoResponseDTO(User user, Long countLike, Long countScrap) {
        this.userId = user.getId();
        this.type = user.getType();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.grade = user.getGrade();
        this.countLike = countLike;
        this.countScrap = countScrap;
    }

    public static GetUserInfoResponseDTO of(User user, Long countLike, Long countScrap) {
        return GetUserInfoResponseDTO.builder()
                .user(user)
                .countLike(countLike)
                .countScrap(countScrap)
                .build();
    }
}
