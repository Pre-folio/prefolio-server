package prefolio.prefolioserver.query;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import prefolio.prefolioserver.domain.Post;
import prefolio.prefolioserver.domain.Scrap;
import prefolio.prefolioserver.domain.User;

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
            return cb.or(predicates.toArray(predicates.toArray(new Predicate[0])));
        };
    }

    public static Specification<Scrap> likePostActTag(List<String> actTagList) {
        return (root, query, cb) -> {
            Join<Post, Scrap> join = root.join("post", JoinType.INNER);
            List<Predicate> predicates = new ArrayList<>();
            for (String actTag : actTagList) {
                predicates.add(cb.like(join.get("actTag"), '%' + actTag + '%'));
            }
            return cb.or(predicates.toArray(predicates.toArray(new Predicate[0])));
        };
    }
}
