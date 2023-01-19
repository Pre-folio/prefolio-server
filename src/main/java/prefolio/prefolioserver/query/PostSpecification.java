package prefolio.prefolioserver.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import prefolio.prefolioserver.domain.Post;

public class PostSpecification {

    public static Specification<Post> likePartTag(String partTag) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("partTag"), '%' + partTag + '%');
    }

    public static Specification<Post> likeActTag(String actTag) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("actTag"), '%' + actTag + '%');
    }
}
