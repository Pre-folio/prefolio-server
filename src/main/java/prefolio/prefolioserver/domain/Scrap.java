package prefolio.prefolioserver.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
public class Scrap {

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
