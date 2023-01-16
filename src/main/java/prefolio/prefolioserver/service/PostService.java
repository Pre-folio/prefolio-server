package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.OAuth;
import prefolio.prefolioserver.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.dto.response.AddPostResponseDTO;
import prefolio.prefolioserver.dto.response.ClickLikeResponseDTO;
import prefolio.prefolioserver.dto.response.GetPostResponseDTO;

@Service
public interface PostService {

    public AddPostResponseDTO savePost(AddPostRequestDTO addPostDTO);

    public GetPostResponseDTO findPostById(Long postId);

//    public ClickLikeResponseDTO clickLike(Long postId, Boolean isLiked);
//
//    public ClickScrapResponseDTO clickScrap(Long postId, Boolean isScrapped);
}
