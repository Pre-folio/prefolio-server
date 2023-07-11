package prefolio.prefolioserver.domain.post.query;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import prefolio.prefolioserver.domain.post.domain.Like;
import prefolio.prefolioserver.domain.post.domain.Post;
import prefolio.prefolioserver.domain.user.domain.User;

public class LikeSpecification {

    public static Specification<Like> likeCount(User user) {
        return (root, query, cb) -> {
            Join<Like, Post> join = root.join("post", JoinType.INNER);
            return cb.equal(join.get("user"), user);
        };
    }
}
