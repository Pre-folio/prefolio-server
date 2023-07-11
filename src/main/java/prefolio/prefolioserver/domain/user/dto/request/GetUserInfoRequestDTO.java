package prefolio.prefolioserver.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.user.domain.User;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserInfoRequestDTO {
    private String type;

    private String nickname;

    private String profileImage;

    private Integer grade;

    private String refreshToken;

    private Long countScrap;

    private Long countLike;


    /* Dto -> Entity */
    public GetUserInfoRequestDTO (User user, Long countScrap, Long countLike) {
        this.type = user.getType();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.grade = user.getGrade();
        this.refreshToken = user.getRefreshToken();
        this.countScrap = countScrap;
        this.countLike = countLike;
    }
}
