package prefolio.prefolioserver.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "OAuth")
@NoArgsConstructor
public class OAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Builder
    public OAuth(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
