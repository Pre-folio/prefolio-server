package prefolio.prefolioserver.dto;

import jakarta.persistence.Id;
import prefolio.prefolioserver.domain.Post;

public class AddPostResponseDTO {

    @Id
    private Long id;

    public AddPostResponseDTO(Long id) {
        this.id = id;
    }
}
