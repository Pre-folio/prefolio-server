package prefolio.prefolioserver.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.Post;
import prefolio.prefolioserver.domain.constant.ActTag;
import prefolio.prefolioserver.domain.constant.PartTag;
import prefolio.prefolioserver.domain.constant.SortBy;
import prefolio.prefolioserver.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.dto.response.*;

import java.util.List;

@Service
public interface PostService {

    public MainPostResponseDTO getAllPosts(
            UserDetailsImpl authUser, SortBy sortBy, PartTag partTag,
            ActTag actTag, Integer pageNum, Integer limit
    );

    MainPostResponseDTO getSearchPosts(
            UserDetailsImpl authUser, SortBy sortBy, PartTag partTag,
            ActTag actTag, Integer pageNum, Integer limit, String searchWord
    );

    public AddPostResponseDTO savePost(UserDetailsImpl authUser, AddPostRequestDTO addPostDTO);

    public GetPostResponseDTO findPostById(UserDetailsImpl authUser, Long postId);

    public ClickLikeResponseDTO clickLike(UserDetailsImpl authUser, Long postId, Boolean isLiked);

    public ClickScrapResponseDTO clickScrap(UserDetailsImpl authUser, Long postId, Boolean isScrapped);

    public List<CardPostResponseDTO> findScrapByUserId(UserDetailsImpl authUser);

    public List<CardPostResponseDTO> findPostByUserId(Long userId);
}
