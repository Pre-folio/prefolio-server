package prefolio.prefolioserver.domain.post.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import prefolio.prefolioserver.domain.post.dto.PostDTO;
import prefolio.prefolioserver.domain.post.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.domain.user.domain.User;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Table(name = "Posts")
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE posts SET deleted_at = CURRENT_TIMESTAMP where id = ?")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Cascade
    @JsonBackReference
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column
    private String thumbnail;

    @Column
    private String title;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column
    private Integer contribution;

    @Column
    private String task;

    @Column
    private String tools;

    @Column(name = "part_tag")
    private String partTag;

    @Column(name = "act_tag")
    private String actTag;

    @Column
    private String contents;

    @Column
    private Integer hits;

    @Column
    private Integer likes;

    @Column
    private Integer scraps;

    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    private List<Like> likeList;

    @OneToMany(mappedBy = "post")
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
    private Post(
            User user,
            String thumbnail,
            String title,
            String startDate,
            String endDate,
            Integer contribution,
            String task,
            String tools,
            String partTag,
            String actTag,
            String contents,
            Integer hits,
            Integer likes,
            Integer scraps,
            Date createdAt,
            Date updatedAt,
            Date deletedAt
    ) {
        this.user = user;
        this.thumbnail = thumbnail;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contribution = contribution;
        this.task = task;
        this.tools = tools;
        this.partTag = partTag;
        this.actTag = actTag;
        this.contents = contents;
        this.hits = hits;
        this.likes = likes;
        this.scraps = scraps;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // 정적 팩토리 메소드
    public static Post of(User user, AddPostRequestDTO postDTO, Integer hits, Integer likes, Integer scraps, Date createdAt) {
        return Post.builder()
                .user(user)
                .thumbnail(postDTO.getThumbnail())
                .title(postDTO.getTitle())
                .startDate(postDTO.getStartDate())
                .endDate(postDTO.getEndDate())
                .contribution(postDTO.getContribution())
                .task(postDTO.getTask())
                .tools(postDTO.getTools())
                .partTag(postDTO.getPartTag())
                .actTag(postDTO.getActTag())
                .contents(postDTO.getContents())
                .hits(hits)
                .likes(likes)
                .scraps(scraps)
                .createdAt(createdAt)
                .build();
    }

    public void update(AddPostRequestDTO addPostRequestDTO) {
        this.thumbnail = addPostRequestDTO.getThumbnail();
        this.title = addPostRequestDTO.getTitle();
        this.startDate = addPostRequestDTO.getStartDate();
        this.endDate = addPostRequestDTO.getEndDate();
        this.contribution = addPostRequestDTO.getContribution();
        this.task = addPostRequestDTO.getTask();
        this.tools = addPostRequestDTO.getTools();
        this.partTag = addPostRequestDTO.getPartTag();
        this.actTag = addPostRequestDTO.getActTag();
        this.contents = addPostRequestDTO.getContents();
        this.updatedAt = new Date();
    }

    // 조회수 업데이트
    public void updateHits(Integer hits) {
        this.hits = hits;
    }

    // 좋아요 업데이트
    public void updateLikes(Integer likes) {
        this.likes = likes;
    }

    // 스크랩수 업데이트
    public void updateScraps(Integer scraps) {
        this.scraps = scraps;
    }
}
