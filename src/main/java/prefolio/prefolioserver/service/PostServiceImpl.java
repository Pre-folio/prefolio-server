package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.Post;
import prefolio.prefolioserver.dto.AddPostDTO;
import prefolio.prefolioserver.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    public AddPostDTO.Response savePost(AddPostDTO.Request addPostRequest) {
        System.out.println("PostServiceImpl.savePost");
        System.out.println("request 기여도 = " + addPostRequest.getContribution());
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
        System.out.println("post Entity = " + post.getTitle());
        Post savedPost = postRepository.saveAndFlush(post);
        return new AddPostDTO.Response(savedPost);
    }
}
