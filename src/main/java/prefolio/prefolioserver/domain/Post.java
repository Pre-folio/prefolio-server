package prefolio.prefolioserver.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@Table(name = "Post")
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Cascade
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
    private String tools;

    @Column(name = "part_tag")
    private String partTag;

    @Column(name = "act_tag")
    private String actTag;

    @Column
    private String contents;

    @Column
    private Integer hits;

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
            String tools,
            String partTag,
            String actTag,
            String contents,
            Integer hits,
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
        this.tools = tools;
        this.partTag = partTag;
        this.actTag = actTag;
        this.contents = contents;
        this.hits = hits;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
