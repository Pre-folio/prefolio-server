package prefolio.prefolioserver.dto;

import lombok.Getter;
import prefolio.prefolioserver.domain.User;

import java.sql.Date;

@Getter
public class UserInfoRequestDTO {

    private Long id;

    private String type;

    private String nickname;

    private String profileImage;

    private Integer grade;

    private String refreshToken;

    private Date createdAt;

    public UserInfoRequestDTO(User user) {
        this.id = user.getId();
        this.type = user.getType();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.grade = user.getGrade();
        this.refreshToken = user.getRefreshToken();
        this.createdAt = (Date) user.getCreatedAt();
    }
}
