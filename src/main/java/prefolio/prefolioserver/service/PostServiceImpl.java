package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
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
public class PostServiceImpl implements PostService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final ScrapRepository scrapRepository;

    @Override
    public MainPostResponseDTO getAllPosts(
            UserDetailsImpl authUser, SortBy sortBy, PartTag partTag,
            ActTag actTag, Integer pageNum, Integer limit
    ) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저는 존재하지 않습니다."));

        // 페이지 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by(sortBy.getSortBy()).descending());

        // 쿼리
        Specification<Post> spec = (root, query, criteriaBuilder) -> null;
        if (partTag != null)  // 쿼리에 partTag 들어왔을 때
            spec = spec.and(PostSpecification.likePartTag(partTag.getPartTag()));
        else if (partTag == null)  // 쿼리에 partTag 없으면 로그인 유저 part 정보로 쿼리
            spec = spec.and(PostSpecification.likePartTag(user.getType()));
        if (actTag != null)
            spec = spec.and(PostSpecification.likeActTag(actTag.getActTag()));

        Page<Post> findPosts = postRepository.findAll(spec, pageRequest);
        List<MainPostDTO> mainPostsList = new ArrayList<>();
        for (Post p : findPosts.getContent()) {
            String pTag = p.getPartTag();
            String aTag = p.getActTag();
            MainPostDTO mainPostDTO = new MainPostDTO(p, parseTag(pTag), parseTag(aTag));
            mainPostsList.add(mainPostDTO);
        }

        return new MainPostResponseDTO(mainPostsList, findPosts.getTotalPages(), findPosts.getTotalElements());
    }

    @Override
    public MainPostResponseDTO getSearchPosts(
            UserDetailsImpl authUser, SortBy sortBy, PartTag partTag,
            ActTag actTag, Integer pageNum, Integer limit, String searchWord
    ) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저는 존재하지 않습니다."));

        // 페이지 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by(sortBy.getSortBy()).descending());

        // 쿼리
        Specification<Post> spec = PostSpecification.likeTitle(searchWord)  // 검색
                .or(PostSpecification.likeContents(searchWord));
        if (partTag != null)  // 쿼리에 partTag 들어왔을 때
            spec = spec.and(PostSpecification.likePartTag(partTag.getPartTag()));
        else if (partTag == null)  // 쿼리에 partTag 없으면 로그인 유저 part 정보로 쿼리
            spec = spec.and(PostSpecification.likePartTag(user.getType()));
        if (actTag != null)
            spec = spec.and(PostSpecification.likeActTag(actTag.getActTag()));

        Page<Post> findPosts = postRepository.findAll(spec, pageRequest);
        List<MainPostDTO> mainPostsList = new ArrayList<>();
        for (Post p : findPosts.getContent()) {
            String pTag = p.getPartTag();
            String aTag = p.getActTag();
            MainPostDTO mainPostDTO = new MainPostDTO(p, parseTag(pTag), parseTag(aTag));
            mainPostsList.add(mainPostDTO);
        }

        return new MainPostResponseDTO(mainPostsList, findPosts.getTotalPages(), findPosts.getTotalElements());
    }

    public List<String> parseTag(String strTag) {
        List<String> tagList = new ArrayList<>();
        // 태그 없을 때
        if (strTag == null || strTag == "") {
            return tagList;
        }
        // 태그 있을 때 파싱
        String[] splitTag = strTag.split(",");
        for (String t : splitTag) {
            tagList.add(t);
        }
        return tagList;
    }

    @Override
    public AddPostResponseDTO savePost(UserDetailsImpl authUser, AddPostRequestDTO addPostRequest) {
        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));

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
        return new AddPostResponseDTO(savedPost);
    }

    @Override
    public GetPostResponseDTO findPostById(UserDetailsImpl authUser, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("해당 계시글을 찾을 수 없습니다."));
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저는 존재하지 않습니다."));

        PostDTO postDTO = new PostDTO(post);
        CountDTO countDTO = new CountDTO(post.getHits(), post.getLikeList().size(), post.getScrapList().size());
        UserDTO userDTO = new UserDTO(user);

        Boolean isLiked = false;
        Boolean isScrapped = false;

        Optional<Like> like = likeRepository.findByUserIdAndPostId(user.getId(), post.getId());
        Optional<Scrap> scrap = scrapRepository.findByUserIdAndPostId(user.getId(), post.getId());

        if(like.isPresent()) {
            isLiked = true;
        }
        if(scrap.isPresent()) {
            isScrapped = true;
        }

        return new GetPostResponseDTO(postDTO, countDTO, userDTO, isLiked, isScrapped);
    }

    @Override
    public ClickLikeResponseDTO clickLike(UserDetailsImpl authUser, Long postId, Boolean isLiked) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("해당 게시글은 존재하지 않습니다."));
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저는 존재하지 않습니다."));
        // 좋아요 누름
        if (isLiked == Boolean.TRUE) {
            Like like = Like.builder().user(user)
                    .post(post)
                    .build();
            likeRepository.save(like);
        } else { // 좋아요 취소
            Like like = likeRepository.findByUserIdAndPostId(user.getId(), postId).get();
            likeRepository.delete(like);
        }
        Long likes = likeRepository.countByPostId(postId);
        post.setLikes(post.getLikeList().size());
        postRepository.save(post);
        return new ClickLikeResponseDTO(likes, isLiked);
    }

    @Override
    public ClickScrapResponseDTO clickScrap(UserDetailsImpl authUser, Long postId, Boolean isScrapped) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("해당 게시글은 존재하지 않습니다."));
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저는 존재하지 않습니다."));
        // 스크랩 누름
        if (isScrapped == Boolean.TRUE) {
            Scrap scrap = Scrap.builder().user(user)
                    .post(post)
                    .build();
            scrapRepository.save(scrap);
        } else { // 스크랩 취소
            Scrap scrap = scrapRepository.findByUserIdAndPostId(user.getId(), postId).get();
            scrapRepository.delete(scrap);
        }
        Long scraps = scrapRepository.countByPostId(postId);
        post.setScraps(post.getScrapList().size());
        postRepository.save(post);
        return new ClickScrapResponseDTO(scraps, isScrapped);
    }

    @Override
    public CardPostResponseDTO findPostByUserId(
            Long userId, PartTag partTag, ActTag actTag, Integer pageNum, Integer limit) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(pageNum, limit);

        Specification<Post> spec = (root, query, criteriaBuilder) -> null;

        if (partTag!=null){
            spec = spec.and(PostSpecification.likePartTag(partTag.getPartTag()));
        }
        if (actTag!=null){
            spec = spec.and(PostSpecification.likeActTag(actTag.getActTag()));
        }
        spec = spec.and(PostSpecification.equalUser(user));
        Page<Post> findPosts = Optional.ofNullable(postRepository.findAll(spec, pageRequest))
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        List<CardPostDTO> cardPostsList = new ArrayList<>();

        for(Post post : findPosts){
            String pTag = post.getPartTag();
            String aTag = post.getActTag();
            CardPostDTO dto = new CardPostDTO(post, parseTag(pTag), parseTag(aTag));
            cardPostsList.add(dto);
        }

        return new CardPostResponseDTO(cardPostsList, findPosts.getTotalPages(), findPosts.getTotalElements());
    }

    @Override
    public CardPostResponseDTO findScrapByUserId(
            UserDetailsImpl authUser, PartTag partTag, ActTag actTag, Integer pageNum, Integer limit) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        
        PageRequest pageRequest = PageRequest.of(pageNum, limit);
        
        Specification<Scrap> spec = (root, query, criteriaBuilder) -> null;

        if (partTag!=null){
            spec = spec.and(ScrapSpecification.likePostPartTag(partTag.getPartTag()));
        }
        if (actTag!=null){
            spec = spec.and(ScrapSpecification.likePostActTag(actTag.getActTag()));
        }
        spec = spec.and(ScrapSpecification.equalUser(user));

        Page<Scrap> findScraps = Optional.ofNullable(scrapRepository.findAll(spec, pageRequest))
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        List<CardPostDTO> cardScrapsDTOList = new ArrayList<>();

        for(Scrap scrap : findScraps){
            String pTag = scrap.getPost().getPartTag();
            String aTag = scrap.getPost().getActTag();
            CardPostDTO dto = new CardPostDTO(scrap, parseTag(pTag), parseTag(aTag));
            cardScrapsDTOList.add(dto);
        }

        return new CardPostResponseDTO(cardScrapsDTOList, findScraps.getTotalPages(), findScraps.getTotalElements());
    }
}
