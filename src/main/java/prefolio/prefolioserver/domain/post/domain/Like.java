package prefolio.prefolioserver.domain.post.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prefolio.prefolioserver.domain.user.domain.User;

@Entity
@Getter
@Table(name = "Likes")
@NoArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Cascade
    @JsonBackReference
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    private Like(Long id, User user, Post post) {
        this.id = id;
        this.user = user;
        this.post = post;
    }

    public static Like of(Long id, User user, Post post) {
        return Like.builder()
                .id(id)
                .user(user)
                .post(post)
                .build();
    }
}
