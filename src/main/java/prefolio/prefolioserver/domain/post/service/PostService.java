package prefolio.prefolioserver.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prefolio.prefolioserver.domain.post.domain.Like;
import prefolio.prefolioserver.domain.post.domain.Post;
import prefolio.prefolioserver.domain.post.domain.Scrap;
import prefolio.prefolioserver.domain.post.domain.SortBy;
import prefolio.prefolioserver.domain.post.dto.*;
import prefolio.prefolioserver.domain.post.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.domain.post.dto.response.*;
import prefolio.prefolioserver.domain.post.mapper.PostMapper;
import prefolio.prefolioserver.domain.post.repository.LikeRepository;
import prefolio.prefolioserver.domain.post.repository.PostRepository;
import prefolio.prefolioserver.domain.post.repository.ScrapRepository;
import prefolio.prefolioserver.domain.user.domain.User;
import prefolio.prefolioserver.domain.user.repository.UserRepository;
import prefolio.prefolioserver.domain.user.service.UserDetailsImpl;
import prefolio.prefolioserver.domain.user.dto.UserDTO;
import prefolio.prefolioserver.global.error.CustomException;
import prefolio.prefolioserver.domain.post.query.PostSpecification;
import prefolio.prefolioserver.domain.post.query.ScrapSpecification;

import java.util.*;

import static prefolio.prefolioserver.global.error.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class PostService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final ScrapRepository scrapRepository;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public MainPostResponseDTO getAllPosts(
            UserDetailsImpl authUser, SortBy sortBy, String partTagList,
            String actTagList, Integer pageNum, Integer limit
    ) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 페이지 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by(sortBy.getSortBy()).descending());

        // 쿼리
        Specification<Post> spec = postMapper.toPostTagQuery(partTagList, actTagList);

        Page<Post> findPosts = postRepository.findAll(spec, pageRequest);
        List<MainPostDTO> mainPostsList = postMapper.toMainPostDTOList(findPosts, user);

        return MainPostResponseDTO.of(mainPostsList, findPosts.getTotalPages(), findPosts.getTotalElements());
    }

    @Transactional(readOnly = true)
    public MainPostResponseDTO getSearchPosts(
            UserDetailsImpl authUser, SortBy sortBy, String partTagList,
            String actTagList, Integer pageNum, Integer limit, String searchWord
    ) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 페이지 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by(sortBy.getSortBy()).descending());

        // 쿼리
        Specification<Post> spec = postMapper.toSearchPostTagQuery(searchWord, partTagList, actTagList);

        Page<Post> findPosts = postRepository.findAll(spec, pageRequest);
        List<MainPostDTO> mainPostsList = postMapper.toMainPostDTOList(findPosts, user);

        return MainPostResponseDTO.of(mainPostsList, findPosts.getTotalPages(), findPosts.getTotalElements());
    }

    @Transactional
    public PostIdResponseDTO savePost(UserDetailsImpl authUser, AddPostRequestDTO addPostRequest) {
        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 게시물 생성
        Post post = Post.of(findUser, addPostRequest, 0, 0, 0, new Date());

        Post savedPost = postRepository.saveAndFlush(post);

        return PostIdResponseDTO.from(savedPost);
    }

    @Transactional
    public PostIdResponseDTO updatePost(UserDetailsImpl authUser, Long postId, AddPostRequestDTO addPostRequest) {
        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Post findPost = postRepository.findByIdAndUserId(postId, findUser.getId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        findPost.update(addPostRequest);
        postRepository.save(findPost);

        return PostIdResponseDTO.from(findPost);
    }

    @Transactional
    public PostIdResponseDTO deletePost(UserDetailsImpl authUser, Long postId) {
        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Post findPost = postRepository.findByIdAndUserId(postId, findUser.getId())
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        postRepository.deleteById(findPost.getId());
        likeRepository.deleteByPostId(findPost.getId());
        scrapRepository.deleteByPostId(findPost.getId());

        return PostIdResponseDTO.from(findPost);
    }

    @Transactional
    public GetPostResponseDTO findPostById(UserDetailsImpl authUser, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        User postUser = userRepository.findByEmail(post.getUser().getEmail())
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        User loginUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        post.updateHits(post.getHits() + 1);    // 조회수 증가
        Post savedPost = postRepository.saveAndFlush(post);
        TagListPostDTO tagListPostDTO = TagListPostDTO.of(savedPost, postMapper.parseTag(savedPost.getTools()), postMapper.parseTag(savedPost.getPartTag()), postMapper.parseTag(savedPost.getActTag()));
        CountDTO countDTO = CountDTO.of(savedPost.getLikeList().size(), savedPost.getScrapList().size());
        UserDTO userDTO = new UserDTO(postUser);

        Boolean isLiked = postMapper.checkIsLiked(loginUser, savedPost);    // 좋아요 상태 확인
        Boolean isScrapped = postMapper.checkIsScrapped(loginUser, savedPost);  // 스크랩 상태 확인

        return GetPostResponseDTO.of(tagListPostDTO, countDTO, userDTO, isLiked, isScrapped);
    }


    @Transactional
    public ClickLikeResponseDTO clickLike(UserDetailsImpl authUser, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 좋아요 상태 확인
        Boolean isLiked = postMapper.checkIsLiked(user, post);

        // 좋아요 누름
        Long likes = postMapper.clickLike(isLiked, user, post);

        return ClickLikeResponseDTO.of(likes, isLiked);
    }


    @Transactional
    public ClickScrapResponseDTO clickScrap(UserDetailsImpl authUser, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 스크랩 상태 확인
        Boolean isScrapped = postMapper.checkIsScrapped(user, post);

        // 스크랩 누름
        Long scraps = postMapper.clickScrap(isScrapped, user, post);

        return ClickScrapResponseDTO.of(scraps, !isScrapped);
    }


    @Transactional(readOnly = true)
    public CardPostResponseDTO findPostByUserId(
            UserDetailsImpl authUser, Long userId, String partTagList,
            String actTagList, Integer pageNum, Integer limit) {

        User me = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 페이지 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by("createdAt").descending());

        // 쿼리
        Specification<Post> spec = postMapper.toPostTagQuery(partTagList, actTagList);
        spec = spec.and(PostSpecification.equalUser(user));
        Page<Post> findPosts = postRepository.findAll(spec, pageRequest);

        List<CardPostDTO> cardPostsDTOList = postMapper.postToCardPostDTOList(findPosts, me, user);

        return CardPostResponseDTO.of(cardPostsDTOList, findPosts.getTotalPages(), findPosts.getTotalElements());
    }

    @Transactional(readOnly = true)
    public CardPostResponseDTO findMyScrap(
            UserDetailsImpl authUser, String partTagList, String actTagList, Integer pageNum, Integer limit) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 페이지 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by("id").descending());

        // 쿼리
        Specification<Scrap> spec = postMapper.toScrapTagQuery(partTagList, actTagList);
        spec = spec.and(ScrapSpecification.equalUser(user));

        Page<Scrap> findScraps = scrapRepository.findAll(spec, pageRequest);

        List<CardPostDTO> cardScrapsDTOList = postMapper.scrapToCardPostDTOList(findScraps);

        return CardPostResponseDTO.of(cardScrapsDTOList, findScraps.getTotalPages(), findScraps.getTotalElements());
    }
}
