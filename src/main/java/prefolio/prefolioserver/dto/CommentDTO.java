package prefolio.prefolioserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.Comment;
import prefolio.prefolioserver.domain.User;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private String nickname;
    private String profileImage;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date createdAt;

    public CommentDTO(Comment comment){
        this.nickname = comment.getUser().getNickname();
        this.profileImage = comment.getUser().getProfileImage();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
    }
}
