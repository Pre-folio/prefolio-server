package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.dto.AddPostDTO;
import prefolio.prefolioserver.dto.ClickLikeDTO;
import prefolio.prefolioserver.dto.ClickScrapDTO;
import prefolio.prefolioserver.dto.GetPostDTO;

@Service
public interface PostService {

    AddPostDTO.Response savePost(AddPostDTO.Request addPostDTO);

    GetPostDTO.Response findPostById(Long postId);

//    ClickLikeDTO.Response clickLike(Long postId, Boolean isLiked);
//
//    ClickScrapDTO.Response clickScrap(Long postId, Boolean isScrapped);
}
