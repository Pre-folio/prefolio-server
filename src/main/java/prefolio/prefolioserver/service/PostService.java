package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.dto.response.*;

import java.util.List;

@Service
public interface PostService {

    public AddPostResponseDTO savePost(UserDetailsImpl authUser, AddPostRequestDTO addPostDTO);

    public GetPostResponseDTO findPostById(UserDetailsImpl authUser, Long postId);

    public ClickLikeResponseDTO clickLike(UserDetailsImpl authUser, Long postId, Boolean isLiked);

    public ClickScrapResponseDTO clickScrap(UserDetailsImpl authUser, Long postId, Boolean isScrapped);

    List<GetScrapResponseDTO> findScrapByUserId(UserDetailsImpl authUser);
}
