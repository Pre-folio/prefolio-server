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

    @Column

    private Boolean isMember;

    @Builder
    public OAuth(String email, Boolean isMember) {
        this.email = email;
        this.isMember = isMember;
    }
}
