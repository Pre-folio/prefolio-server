package prefolio.prefolioserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.User;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserInfoResponseDTO {

    private Long userId;
    private String type;
    private String nickname;
    private String profileImage;
    private Integer grade;
    private Long countScrap;
    private Long countLike;


    /* Entity -> Dto */
    public GetUserInfoResponseDTO(User user, Long countScrap, Long countLike) {
        this.userId = user.getId();
        this.type = user.getType();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.grade = user.getGrade();
        this.countScrap = countScrap;
        this.countLike = countLike;
    }
}
