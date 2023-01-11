package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.dto.AddPostDTO;

@Service
public interface PostService {

    AddPostDTO.Response savePost(AddPostDTO.Request addPostDTO);
}
