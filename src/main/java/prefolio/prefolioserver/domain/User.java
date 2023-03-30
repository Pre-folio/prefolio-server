package prefolio.prefolioserver.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Entity
@Table(name = "Users")
@NoArgsConstructor
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String type;

    @Column
    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

    @Column
    private Integer grade;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Post> postList;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Like> likeList;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Scrap> scrapList;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date createdAt;

    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date updatedAt;

    @Column(name = "deleted_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date deletedAt;

    @Builder
    public User(
            Long id,
            String email,
            String type,
            String nickname,
            String profileImage,
            Integer grade,
            String refreshToken,
            Date createdAt,
            Date updatedAt,
            Date deletedAt
    ) {
        this.id = id;
        this.email = email;
        this.type = type;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.grade = grade;
        this.refreshToken = refreshToken;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
