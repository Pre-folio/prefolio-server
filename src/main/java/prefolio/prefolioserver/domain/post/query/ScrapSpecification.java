package prefolio.prefolioserver.domain.post.query;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import prefolio.prefolioserver.domain.post.domain.Post;
import prefolio.prefolioserver.domain.post.domain.Scrap;
import prefolio.prefolioserver.domain.user.domain.User;

import java.util.ArrayList;
import java.util.List;

public class ScrapSpecification {

    public static Specification<Scrap> equalUser(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }

    public static  Specification<Scrap> likePostPartTag(List<String> partTagList) {
        return (root, query, cb) -> {
            Join<Post, Scrap> join = root.join("post", JoinType.INNER);
            List<Predicate> predicates = new ArrayList<>();
            for (String partTag : partTagList) {
                predicates.add(cb.like(join.get("partTag"), '%' + partTag + '%'));
            }
            return cb.and(predicates.toArray(predicates.toArray(new Predicate[0])));
        };
    }

    public static Specification<Scrap> likePostActTag(List<String> actTagList) {
        return (root, query, cb) -> {
            Join<Post, Scrap> join = root.join("post", JoinType.INNER);
            List<Predicate> predicates = new ArrayList<>();
            for (String actTag : actTagList) {
                predicates.add(cb.like(join.get("actTag"), '%' + actTag + '%'));
            }
            return cb.and(predicates.toArray(predicates.toArray(new Predicate[0])));
        };
    }

    public static Specification<Scrap> likeCount(User user) {
        return (root, query, cb) -> {
            Join<Scrap, Post> join = root.join("post", JoinType.INNER);
            return cb.equal(join.get("user"), user);
        };
    }
}
