package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.*;
import prefolio.prefolioserver.dto.*;
import prefolio.prefolioserver.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.dto.response.AddPostResponseDTO;
import prefolio.prefolioserver.dto.response.ClickLikeResponseDTO;
import prefolio.prefolioserver.dto.response.ClickScrapResponseDTO;
import prefolio.prefolioserver.dto.response.GetPostResponseDTO;
import prefolio.prefolioserver.repository.LikeRepository;
import prefolio.prefolioserver.repository.PostRepository;
import prefolio.prefolioserver.repository.ScrapRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final ScrapRepository scrapRepository;

    @Override
    public AddPostResponseDTO savePost(AddPostRequestDTO addPostRequest) {
        Post post = Post.builder().thumbnail(addPostRequest.getThumbnail())
                .title(addPostRequest.getTitle())
                .startDate(addPostRequest.getStartDate())
                .endDate(addPostRequest.getEndDate())
                .contribution(addPostRequest.getContribution())
                .tools(addPostRequest.getTools())
                .partTag(addPostRequest.getPartTag())
                .actTag(addPostRequest.getActTag())
                .contents(addPostRequest.getContents())
                .build();
        Post savedPost = postRepository.saveAndFlush(post);
        return new AddPostResponseDTO(savedPost);
    }

    @Override
    public GetPostResponseDTO findPostById(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        return null;
    }

//    @Override
//    public ClickLikeResponseDTO clickLike(Long postId, Boolean isLiked) {
//        Post post = postRepository.findById(postId).get();
//        User user = clickLikeRequest.getUser();
//        // 좋아요 누름
//        if (isLiked == Boolean.TRUE) {
//            Like like = Like.builder().user(user)
//                    .post(post)
//                    .build();
//            likeRepository.save(like);
//        } else { // 좋아요 취소
//            Like like = likeRepository.findByUserIdAndPostId(user.getId(), postId).get();
//            likeRepository.delete(like);
//        }
//        Long likes = likeRepository.countByPostId(postId);
//        return new ClickLikeResponseDTO(likes, isLiked);
//    }
//
//    @Override
//    public ClickScrapResponseDTO clickScrap(Long postId, Boolean isScrapped) {
//        Post post = postRepository.findById(postId).get();
//        User user = clickScrapRequest.getUser();
//        // 스크랩 누름
//        if (isScrapped == Boolean.TRUE) {
//            Scrap scrap = Scrap.builder().user(user)
//                    .post(post)
//                    .build();
//            scrapRepository.save(scrap);
//        } else { // 스크랩 취소
//            Scrap scrap = scrapRepository.findByUserIdAndPostId(user.getId(), postId).get();
//            scrapRepository.delete(scrap);
//        }
//        Long scraps = scrapRepository.countByPostId(postId);
//        return new ClickScrapResponseDTO(scraps, isScrapped);
//    }
//
}
