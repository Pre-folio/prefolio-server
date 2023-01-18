package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.dto.response.AddPostResponseDTO;
import prefolio.prefolioserver.dto.response.ClickLikeResponseDTO;
import prefolio.prefolioserver.dto.response.ClickScrapResponseDTO;
import prefolio.prefolioserver.dto.response.GetPostResponseDTO;

@Service
public interface PostService {

    public AddPostResponseDTO savePost(UserDetailsImpl authUser, AddPostRequestDTO addPostDTO);

    public GetPostResponseDTO findPostById(UserDetailsImpl authUser, Long postId);

    public ClickLikeResponseDTO clickLike(UserDetailsImpl authUser, Long postId, Boolean isLiked);

    public ClickScrapResponseDTO clickScrap(UserDetailsImpl authUser, Long postId, Boolean isScrapped);
}
