package prefolio.prefolioserver.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.post.domain.Comment;

import java.util.Date;

@Getter
@NoArgsConstructor
public class CommentDTO {

    private String nickname;
    private String profileImage;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date createdAt;

    @Builder
    private CommentDTO(String nickname, String profileImage, String contents, Date createdAt) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public static CommentDTO from(Comment comment){
        return CommentDTO.builder()
                .nickname(comment.getUser().getNickname())
                .profileImage(comment.getUser().getProfileImage())
                .contents(comment.getContents())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
