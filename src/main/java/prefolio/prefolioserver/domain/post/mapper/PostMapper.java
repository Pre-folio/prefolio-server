package prefolio.prefolioserver.domain.post.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import prefolio.prefolioserver.domain.post.domain.Like;
import prefolio.prefolioserver.domain.post.domain.Post;
import prefolio.prefolioserver.domain.post.domain.Scrap;
import prefolio.prefolioserver.domain.post.dto.CardPostDTO;
import prefolio.prefolioserver.domain.post.dto.MainPostDTO;
import prefolio.prefolioserver.domain.post.query.PostSpecification;
import prefolio.prefolioserver.domain.post.query.ScrapSpecification;
import prefolio.prefolioserver.domain.post.repository.LikeRepository;
import prefolio.prefolioserver.domain.post.repository.PostRepository;
import prefolio.prefolioserver.domain.post.repository.ScrapRepository;
import prefolio.prefolioserver.domain.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final ScrapRepository scrapRepository;

    public List<MainPostDTO> toMainPostDTOList(Page<Post> posts, User user) {
        List<MainPostDTO> mainPostsList = new ArrayList<>();
        for (Post p : posts) {
            String pTag = p.getPartTag();
            String aTag = p.getActTag();
            Boolean isScrapped = scrapRepository.countByUserIdAndPostId(user.getId(), p.getId()) != 0;
            Boolean isMine = Objects.equals(p.getUser().getId(), user.getId());
            MainPostDTO mainPostDTO = MainPostDTO.of(p, parseTag(pTag), parseTag(aTag), isScrapped, isMine);
            mainPostsList.add(mainPostDTO);
        }

        return mainPostsList;
    }

    public List<CardPostDTO> postToCardPostDTOList(Page<Post> posts, User me, User user) {
        List<CardPostDTO> cardPostsDTOList = new ArrayList<>();
        for(Post post : posts){
            String pTag = post.getPartTag();
            String aTag = post.getActTag();
            Boolean isScrapped = scrapRepository.countByUserIdAndPostId(me.getId(), post.getId()) != 0;
            Boolean isMine = me==user;
            CardPostDTO dto = CardPostDTO.postToDto(post, parseTag(pTag), parseTag(aTag), isScrapped, isMine);
            cardPostsDTOList.add(dto);
        }

        return cardPostsDTOList;
    }

    public List<CardPostDTO> scrapToCardPostDTOList(Page<Scrap> scraps) {
        List<CardPostDTO> cardScrapsDTOList = new ArrayList<>();
        for(Scrap scrap : scraps){
            String pTag = scrap.getPost().getPartTag();
            String aTag = scrap.getPost().getActTag();
            Boolean isScrapped = true;
            CardPostDTO dto = CardPostDTO.scrapToDto(scrap, parseTag(pTag), parseTag(aTag), isScrapped);
            cardScrapsDTOList.add(dto);
        }

        return cardScrapsDTOList;
    }

    public Specification<Post> toPostTagQuery(String partTagList, String actTagList) {
        Specification<Post> spec = (root, query, criteriaBuilder) -> null;

        if (partTagList != null && partTagList != "")  // 쿼리에 partTag 들어왔을 때
            spec = spec.and(PostSpecification.likePartTag(parseTag(partTagList)));
        if (actTagList != null && actTagList != "")
            spec = spec.and(PostSpecification.likeActTag(parseTag(actTagList)));

        return spec;
    }

    public Specification<Scrap> toScrapTagQuery(String partTagList, String actTagList) {
        Specification<Scrap> spec = (root, query, criteriaBuilder) -> null;

        if (partTagList!=null){
            spec = spec.and(ScrapSpecification.likePostPartTag(parseTag(partTagList)));
        }
        if (actTagList!=null){
            spec = spec.and(ScrapSpecification.likePostActTag(parseTag(actTagList)));
        }
        return spec;
    }

    public Specification<Post> toSearchPostTagQuery(String searchWord, String partTagList, String actTagList) {
        Specification<Post> spec = PostSpecification.likeTitle(searchWord)  // 검색
                .or(PostSpecification.likeContents(searchWord));

        if (partTagList != null && partTagList != "")  // 쿼리에 partTag 들어왔을 때
            spec = spec.and(PostSpecification.likePartTag(parseTag(partTagList)));
        if (actTagList != null && actTagList != "")
            spec = spec.and(PostSpecification.likeActTag(parseTag(actTagList)));

        return spec;
    }

    public Boolean checkIsLiked(User user, Post post) {
        Boolean isLiked = false;
        Optional<Like> like = likeRepository.findByUserIdAndPostId(user.getId(), post.getId());
        if(like.isPresent()) {
            isLiked = true;
        }

        return isLiked;
    }

    // 좋아요 클릭(좋아요, 좋아요 취소 둘다 가능)
    public Long clickLike(Boolean isLiked, User user, Post post) {
        if (isLiked == false) {
            Like like = Like.builder().user(user)
                    .post(post)
                    .build();
            likeRepository.save(like);
        } else if (isLiked == true) { // 좋아요 취소
            Optional<Like> like = likeRepository.findByUserIdAndPostId(user.getId(), post.getId());
            likeRepository.delete(like.get());
        }
        Long likes = likeRepository.countByPostId(post.getId());
        post.updateLikes(post.getLikeList().size());
        postRepository.save(post);

        return likes;
    }

    public Boolean checkIsScrapped(User user, Post post) {
        Boolean isScrapped = false;
        Optional<Scrap> scrap = scrapRepository.findByUserIdAndPostId(user.getId(), post.getId());
        if(scrap.isPresent()) {
            isScrapped = true;
        }

        return isScrapped;
    }

    // 스크랩 클릭(스크랩, 스크랩 취소 둘다 가능)
    public Long clickScrap(Boolean isScrapped, User user, Post post) {
        if (isScrapped == false) {
            Scrap scrap = Scrap.builder()
                    .user(user)
                    .post(post)
                    .build();
            scrapRepository.save(scrap);
        } else if (isScrapped == true) { // 스크랩 취소
            Optional<Scrap> scrap = scrapRepository.findByUserIdAndPostId(user.getId(), post.getId());
            scrapRepository.delete(scrap.get());
        }
        Long scraps = scrapRepository.countByPostId(post.getId());
        post.updateScraps(post.getScrapList().size());
        postRepository.save(post);

        return scraps;
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
}
