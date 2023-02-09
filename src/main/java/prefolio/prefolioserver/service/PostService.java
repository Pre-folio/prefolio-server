package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.annotation.AuthUser;
import prefolio.prefolioserver.domain.*;
import prefolio.prefolioserver.domain.constant.*;
import prefolio.prefolioserver.dto.*;
import prefolio.prefolioserver.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.dto.response.*;
import prefolio.prefolioserver.error.CustomException;
import prefolio.prefolioserver.query.PostSpecification;
import prefolio.prefolioserver.query.ScrapSpecification;
import prefolio.prefolioserver.repository.*;
import java.util.*;

import static prefolio.prefolioserver.error.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class PostService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final ScrapRepository scrapRepository;


    public MainPostResponseDTO getAllPosts(
            UserDetailsImpl authUser, SortBy sortBy, String partTagList,
            String actTagList, Integer pageNum, Integer limit
    ) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 페이지 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by(sortBy.getSortBy()).descending());

        // 쿼리
        Specification<Post> spec = (root, query, criteriaBuilder) -> null;

        if (partTagList != null && partTagList != "")  // 쿼리에 partTag 들어왔을 때
            spec = spec.and(PostSpecification.likePartTag(parseTag(partTagList)));
//        else if (partTag == null)  // 쿼리에 partTag 없으면 로그인 유저 part 정보로 쿼리
//            spec = spec.and(PostSpecification.likePartTag(user.getType()));
        if (actTagList != null && actTagList != "")
            spec = spec.and(PostSpecification.likeActTag(parseTag(actTagList)));

        Page<Post> findPosts = postRepository.findAll(spec, pageRequest);
        List<MainPostDTO> mainPostsList = new ArrayList<>();
        for (Post p : findPosts) {
            String pTag = p.getPartTag();
            String aTag = p.getActTag();
            Boolean isScrapped = scrapRepository.countByUserIdAndPostId(user.getId(), p.getId()) != 0;
            MainPostDTO mainPostDTO = new MainPostDTO(p, parseTag(pTag), parseTag(aTag), isScrapped);
            mainPostsList.add(mainPostDTO);
        }

        return new MainPostResponseDTO(user.getId(), mainPostsList, findPosts.getTotalPages(), findPosts.getTotalElements());
    }


    public MainPostResponseDTO getSearchPosts(
            UserDetailsImpl authUser, SortBy sortBy, String partTagList,
            String actTagList, Integer pageNum, Integer limit, String searchWord
    ) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 페이지 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by(sortBy.getSortBy()).descending());

        // 쿼리
        Specification<Post> spec = PostSpecification.likeTitle(searchWord)  // 검색
                .or(PostSpecification.likeContents(searchWord));
        if (partTagList != null && partTagList != "")  // 쿼리에 partTag 들어왔을 때
            spec = spec.and(PostSpecification.likePartTag(parseTag(partTagList)));
//        else if (partTag == null)  // 쿼리에 partTag 없으면 로그인 유저 part 정보로 쿼리
//            spec = spec.and(PostSpecification.likePartTag(user.getType()));
        if (actTagList != null && actTagList != "")
            spec = spec.and(PostSpecification.likeActTag(parseTag(actTagList)));

        Page<Post> findPosts = postRepository.findAll(spec, pageRequest);
        List<MainPostDTO> mainPostsList = new ArrayList<>();
        for (Post p : findPosts) {
            String pTag = p.getPartTag();
            String aTag = p.getActTag();
            Boolean isScrapped = scrapRepository.countByUserIdAndPostId(user.getId(), p.getId()) != 0;
            MainPostDTO mainPostDTO = new MainPostDTO(p, parseTag(pTag), parseTag(aTag), isScrapped);
            mainPostsList.add(mainPostDTO);
        }

        return new MainPostResponseDTO(user.getId(), mainPostsList, findPosts.getTotalPages(), findPosts.getTotalElements());
    }

    public List<String> parseTag(String strTag) {
        List<String> tagList = new ArrayList<>();
        // 태그 없을 때
        if (strTag == null || strTag.equals("")) {
            return tagList;
        }
        // 태그 있을 때 파싱
        String[] splitTag = strTag.split(",");
        for (String t : splitTag) {
            tagList.add(t);
        }
        return tagList;
    }


    public PostIdResponseDTO savePost(UserDetailsImpl authUser, AddPostRequestDTO addPostRequest) {
        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Post post = Post.builder()
                .user(findUser)
                .thumbnail(addPostRequest.getThumbnail())
                .title(addPostRequest.getTitle())
                .startDate(addPostRequest.getStartDate())
                .endDate(addPostRequest.getEndDate())
                .contribution(addPostRequest.getContribution())
                .task(addPostRequest.getTask())
                .tools(addPostRequest.getTools())
                .partTag(addPostRequest.getPartTag())
                .actTag(addPostRequest.getActTag())
                .contents(addPostRequest.getContents())
                .hits(0)
                .likes(0)
                .scraps(0)
                .createdAt(new Date())
                .build();
        Post savedPost = postRepository.saveAndFlush(post);
        return new PostIdResponseDTO(savedPost);
    }

    public PostIdResponseDTO updatePost(UserDetailsImpl authUser, Long postId, AddPostRequestDTO addPostRequest) {
        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Post findPost = postRepository.findByIdAndUserId(postId, findUser.getId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        findPost.update(addPostRequest);
        postRepository.save(findPost);

        return new PostIdResponseDTO(findPost);
    }

    public PostIdResponseDTO deletePost(UserDetailsImpl authUser, Long postId) {
        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Post findPost = postRepository.findByIdAndUserId(postId, findUser.getId())
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        postRepository.deleteById(findPost.getId());
        scrapRepository.deleteByPostId(findPost.getId());

        return new PostIdResponseDTO(findPost);
    }

    public GetPostResponseDTO findPostById(UserDetailsImpl authUser, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        User user = userRepository.findByEmail(post.getUser().getEmail())
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        post.setHits(post.getHits() + 1);   // 조회수 증가
        Post savedPost = postRepository.saveAndFlush(post);
        PostDTO postDTO = new PostDTO(
                savedPost, parseTag(savedPost.getTools()), parseTag(savedPost.getPartTag()), parseTag(savedPost.getActTag()));
        CountDTO countDTO = new CountDTO(savedPost.getLikeList().size(), savedPost.getScrapList().size());
        UserDTO userDTO = new UserDTO(user);

        Boolean isLiked = false;
        Boolean isScrapped = false;

        Optional<Like> like = likeRepository.findByUserIdAndPostId(user.getId(), savedPost.getId());
        Optional<Scrap> scrap = scrapRepository.findByUserIdAndPostId(user.getId(), savedPost.getId());

        if(like.isPresent()) {
            isLiked = true;
        }
        if(scrap.isPresent()) {
            isScrapped = true;
        }

        return new GetPostResponseDTO(postDTO, countDTO, userDTO, isLiked, isScrapped);
    }


    public ClickLikeResponseDTO clickLike(UserDetailsImpl authUser, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 좋아요 유무 확인
        Boolean isLiked = true;
        Optional<Like> dbLike = likeRepository.findByUserIdAndPostId(user.getId(), post.getId());
        if (dbLike.isEmpty()) {
            isLiked = false;
        }

        // 좋아요 누름
        if (isLiked == false) {
            Like like = Like.builder().user(user)
                    .post(post)
                    .build();
            likeRepository.save(like);
        } else if (isLiked == true) { // 좋아요 취소
            likeRepository.delete(dbLike.get());
        }
        Long likes = likeRepository.countByPostId(postId);
        post.setLikes(post.getLikeList().size());
        postRepository.save(post);
        return new ClickLikeResponseDTO(likes, isLiked);
    }


    public ClickScrapResponseDTO clickScrap(UserDetailsImpl authUser, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 스크랩 유무 확인
        Boolean isScrapped = true;
        Optional<Scrap> dbScrap = scrapRepository.findByUserIdAndPostId(user.getId(), post.getId());
        if (dbScrap.isEmpty()) {
            isScrapped = false;
        }

        // 스크랩 누름
        if (isScrapped == false) {
            Scrap scrap = Scrap.builder()
                    .user(user)
                    .post(post)
                    .build();
            scrapRepository.save(scrap);
        } else if (isScrapped == true) { // 스크랩 취소
            scrapRepository.delete(dbScrap.get());
        }
        Long scraps = scrapRepository.countByPostId(postId);
        post.setScraps(post.getScrapList().size());
        postRepository.save(post);
        return new ClickScrapResponseDTO(scraps, !isScrapped);
    }


    public CardPostResponseDTO findPostByUserId(
            UserDetailsImpl authUser, Long userId, String partTagList,
            String actTagList, Integer pageNum, Integer limit) {

        User me = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by("createdAt").descending());

        Specification<Post> spec = (root, query, criteriaBuilder) -> null;

        if (partTagList != null && partTagList != ""){
            spec = spec.and(PostSpecification.likePartTag(parseTag(partTagList)));
        }
        if (actTagList != null && actTagList != ""){
            spec = spec.and(PostSpecification.likeActTag(parseTag(actTagList)));
        }
        spec = spec.and(PostSpecification.equalUser(user));
        Page<Post> findPosts = postRepository.findAll(spec, pageRequest);

        List<CardPostDTO> cardPostsList = new ArrayList<>();

        for(Post post : findPosts){
            String pTag = post.getPartTag();
            String aTag = post.getActTag();
            Boolean isScrapped = scrapRepository.countByUserIdAndPostId(me.getId(), post.getId()) != 0;
            CardPostDTO dto = new CardPostDTO(post, parseTag(pTag), parseTag(aTag), isScrapped);
            cardPostsList.add(dto);
        }

        return new CardPostResponseDTO(cardPostsList, findPosts.getTotalPages(), findPosts.getTotalElements());
    }

    public CardPostResponseDTO findMyScrap(
            UserDetailsImpl authUser, String partTagList, String actTagList, Integer pageNum, Integer limit) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by("id").descending());

        Specification<Scrap> spec = (root, query, criteriaBuilder) -> null;

        if (partTagList!=null){
            spec = spec.and(ScrapSpecification.likePostPartTag(parseTag(partTagList)));
        }
        if (actTagList!=null){
            spec = spec.and(ScrapSpecification.likePostActTag(parseTag(actTagList)));
        }
        spec = spec.and(ScrapSpecification.equalUser(user));

        Page<Scrap> findScraps = scrapRepository.findAll(spec, pageRequest);

        List<CardPostDTO> cardScrapsDTOList = new ArrayList<>();

        for(Scrap scrap : findScraps){
            String pTag = scrap.getPost().getPartTag();
            String aTag = scrap.getPost().getActTag();
            Boolean isScrapped = true;
            CardPostDTO dto = new CardPostDTO(scrap, parseTag(pTag), parseTag(aTag), isScrapped);
            cardScrapsDTOList.add(dto);
        }

        return new CardPostResponseDTO(cardScrapsDTOList, findScraps.getTotalPages(), findScraps.getTotalElements());
    }
}
