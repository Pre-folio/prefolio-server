package prefolio.prefolioserver.query;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import prefolio.prefolioserver.domain.Post;
import prefolio.prefolioserver.domain.Scrap;
import prefolio.prefolioserver.domain.User;

public class ScrapSpecification {

    public static Specification<Scrap> equalUser(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }

    public static  Specification<Scrap> likePostPartTag(String partTag) {
        return (root, query, cb) -> {
            Join<Post, Scrap> join = root.join("post", JoinType.INNER);
            return cb.like(join.get("partTag"), '%' + partTag + '%');
        };
    }

    public static Specification<Scrap> likePostActTag(String actTag) {
        return (root, query, cb) -> {
            Join<Post, Scrap> join = root.join("post", JoinType.INNER);
            return cb.like(join.get("actTag"), '%' + actTag + '%');
        };
    }
}
