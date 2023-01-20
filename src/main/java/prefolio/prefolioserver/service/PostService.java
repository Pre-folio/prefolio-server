package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.constant.ActTag;
import prefolio.prefolioserver.domain.constant.PartTag;
import prefolio.prefolioserver.domain.constant.SortBy;
import prefolio.prefolioserver.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.dto.response.*;


@Service
public interface PostService {

    MainPostResponseDTO getAllPosts(
            UserDetailsImpl authUser, SortBy sortBy, PartTag partTag,
            ActTag actTag, Integer pageNum, Integer limit
    );

    MainPostResponseDTO getSearchPosts(
            UserDetailsImpl authUser, SortBy sortBy, PartTag partTag,
            ActTag actTag, Integer pageNum, Integer limit, String searchWord
    );

    AddPostResponseDTO savePost(UserDetailsImpl authUser, AddPostRequestDTO addPostDTO);

    GetPostResponseDTO findPostById(UserDetailsImpl authUser, Long postId);

    ClickLikeResponseDTO clickLike(UserDetailsImpl authUser, Long postId, Boolean isLiked);

    ClickScrapResponseDTO clickScrap(UserDetailsImpl authUser, Long postId, Boolean isScrapped);

    CardPostResponseDTO findPostByUserId(
            Long userId, PartTag partTag, ActTag actTag, Integer pageNum, Integer limit
    );

    CardPostResponseDTO findScrapByUserId(UserDetailsImpl authUser, PartTag partTag, ActTag actTag, Integer pageNum, Integer limit);

}
