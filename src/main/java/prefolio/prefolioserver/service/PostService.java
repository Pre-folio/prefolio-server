package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.dto.AddPostRequestDTO;

@Service
public interface PostService {

    String savePost(AddPostRequestDTO addPostRequestDTO);
}
