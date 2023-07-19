package prefolio.prefolioserver.domain.post.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import prefolio.prefolioserver.domain.post.dto.request.AddCommentRequestDTO;
import prefolio.prefolioserver.domain.user.domain.User;

import java.util.Date;

@Entity
@Getter
@Table(name = "Comments")
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE comments SET deleted_at = CURRENT_TIMESTAMP where id = ?")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Cascade
    @JsonBackReference
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)  // Cascade
    @JsonBackReference
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    @Column
    private String contents;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date createdAt;

    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date updatedAt;

    @Column(name = "deleted_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date deletedAt;

    @Builder
    private Comment(
            Long id,
            User user,
            Post post,
            String contents,
            Date createdAt,
            Date updatedAt,
            Date deletedAt
    ) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Comment of(User user, AddCommentRequestDTO addCommentRequestDTO, Date createdAt) {
        return Comment.builder()
                .user(user)
                .post(addCommentRequestDTO.getPost())
                .contents(addCommentRequestDTO.getContents())
                .createdAt(createdAt)
                .build();
    }

    public void update(AddCommentRequestDTO addCommentRequestDTO) {
        this.contents = addCommentRequestDTO.getContents();
        this.updatedAt = new Date();
    }
}
