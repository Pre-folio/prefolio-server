package prefolio.prefolioserver.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import prefolio.prefolioserver.domain.post.domain.Comment;

import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
    Page<Comment> findAll(Specification<Comment> spec, Pageable pageable);
    Optional<Comment> findByIdAndUserId(Long id, Long userId);
    void deleteById(Long id);
}
