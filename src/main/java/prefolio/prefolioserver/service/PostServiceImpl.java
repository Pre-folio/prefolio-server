package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import prefolio.prefolioserver.domain.*;
import prefolio.prefolioserver.dto.CountDTO;
import prefolio.prefolioserver.dto.PostDTO;
import prefolio.prefolioserver.dto.UserDTO;
import prefolio.prefolioserver.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.dto.response.*;
import prefolio.prefolioserver.error.CustomException;
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
            CardPostResponseDTO dto = new CardPostResponseDTO(scrap);
            cardPostResponseDTOList.add(dto);
        }

        return cardPostResponseDTOList;
    }
}
