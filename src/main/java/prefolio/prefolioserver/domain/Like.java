package prefolio.prefolioserver.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    @JoinColumn(name="userId", nullable = false)
//    private User user;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

}
