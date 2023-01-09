package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.dto.AddPostDTO;
import prefolio.prefolioserver.dto.AddPostResponseDTO;
import prefolio.prefolioserver.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    public String savePost(AddPostDTO addPostDTO) {
        Long postId = postRepository.save(addPostDTO.toEntity()).getId();
//        Optional<Post> post = postRepository.findById(postId);
        return new AddPostResponseDTO(postId);
    }
}
