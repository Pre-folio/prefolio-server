package prefolio.prefolioserver.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.user.domain.User;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String type;
    private String nickname;
    private String profileImage;
    private Integer grade;

    public UserDTO(User user) {
        this.id = user.getId();
        this.type = user.getType();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.grade = user.getGrade();
    }
}
