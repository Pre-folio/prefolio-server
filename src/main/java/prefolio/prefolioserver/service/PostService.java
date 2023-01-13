package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.dto.AddPostDTO;
import prefolio.prefolioserver.dto.ClickLikeDTO;
import prefolio.prefolioserver.dto.ClickScrapDTO;
import prefolio.prefolioserver.dto.GetPostDTO;

@Service
public interface PostService {

    public AddPostDTO.Response savePost(AddPostDTO.Request addPostDTO);

    public GetPostDTO.Response findPostById(Long postId);

//    public ClickLikeDTO.Response clickLike(Long postId, Boolean isLiked);
//
//    public ClickScrapDTO.Response clickScrap(Long postId, Boolean isScrapped);
}
