package prefolio.prefolioserver.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import prefolio.prefolioserver.domain.Post;
import prefolio.prefolioserver.domain.User;
import prefolio.prefolioserver.domain.constant.ActTag;
import prefolio.prefolioserver.domain.constant.PartTag;

import java.util.ArrayList;
import java.util.List;

public class PostSpecification {

    public static Specification<Post> likePartTag(List<String> partTagList) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String partTag : partTagList) {
                predicates.add(criteriaBuilder.like(root.get("partTag"), '%' + partTag + '%'));
            }
            return criteriaBuilder.or(predicates.toArray(predicates.toArray(new Predicate[0])));
        });
    }

    public static Specification<Post> likeActTag(List<String> actTagList) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String actTag : actTagList) {
                predicates.add(criteriaBuilder.like(root.get("actTag"), '%' + actTag + '%'));
            }
            return criteriaBuilder.or(predicates.toArray(predicates.toArray(new Predicate[0])));
        });
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
