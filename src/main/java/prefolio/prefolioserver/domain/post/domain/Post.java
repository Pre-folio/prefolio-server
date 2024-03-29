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
import prefolio.prefolioserver.domain.post.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.domain.user.domain.User;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
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
    public Post(
            Long id,
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
        this.id = id;
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
}
