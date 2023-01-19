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
import prefolio.prefolioserver.domain.constant.ActTag;
import prefolio.prefolioserver.domain.constant.PartTag;
import prefolio.prefolioserver.domain.constant.SortBy;
import prefolio.prefolioserver.dto.CountDTO;
import prefolio.prefolioserver.dto.MainPostDTO;
import prefolio.prefolioserver.dto.PostDTO;
import prefolio.prefolioserver.dto.UserDTO;
import prefolio.prefolioserver.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.dto.response.*;
import prefolio.prefolioserver.error.CustomException;
import prefolio.prefolioserver.query.PostSpecification;
import prefolio.prefolioserver.repository.LikeRepository;
import prefolio.prefolioserver.repository.PostRepository;
import prefolio.prefolioserver.repository.ScrapRepository;
import prefolio.prefolioserver.repository.UserRepository;
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
        // 태크 있을 때 파싱
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
    public List<CardPostResponseDTO> findScrapByUserId(UserDetailsImpl authUser) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

//        List<Scrap> scraps = scrapRepository.findAllByUserId(user.getId())
//                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        List<Scrap> scraps = Optional.ofNullable(scrapRepository.findAllByUserId(user.getId()))
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        List<CardPostResponseDTO> cardPostResponseDTOList = new ArrayList<>();

        for(Scrap scrap : scraps){
            String pTag = scrap.getPost().getPartTag();
            String aTag = scrap.getPost().getActTag();
            CardPostResponseDTO dto = new CardPostResponseDTO(scrap, parseTag(pTag), parseTag(aTag));
            cardPostResponseDTOList.add(dto);
        }

        return cardPostResponseDTOList;
    }
}
