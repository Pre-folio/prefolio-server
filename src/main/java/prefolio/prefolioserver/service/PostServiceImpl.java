package prefolio.prefolioserver.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.Post;
import prefolio.prefolioserver.dto.AddPostRequestDTO;
import prefolio.prefolioserver.dto.AddPostResponseDTO;
import prefolio.prefolioserver.repository.PostRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    public String savePost(AddPostRequestDTO addPostRequestDTO) {
        Long postId = postRepository.save(addPostRequestDTO.toEntity()).getId();
//        Optional<Post> post = postRepository.findById(postId);
        return new AddPostResponseDTO(postId);
    }
}
