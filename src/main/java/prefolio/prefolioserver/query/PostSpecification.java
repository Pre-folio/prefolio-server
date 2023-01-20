package prefolio.prefolioserver.query;

import org.springframework.data.jpa.domain.Specification;
import prefolio.prefolioserver.domain.Post;
import prefolio.prefolioserver.domain.User;

public class PostSpecification {

    public static Specification<Post> likePartTag(String partTag) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("partTag"), '%' + partTag + '%');
    }

    public static Specification<Post> likeActTag(String actTag) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("actTag"), '%' + actTag + '%');
    }

    public static Specification<Post> likeTitle(String searchWord) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), '%' + searchWord + '%');
    }

    public static Specification<Post> likeContents(String searchWord) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("contents"), '%' + searchWord + '%');
    }

    public static Specification<Post> equalUser(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }
}
