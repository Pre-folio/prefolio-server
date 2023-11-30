package prefolio.prefolioserver.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import prefolio.prefolioserver.domain.post.domain.Scrap;
import prefolio.prefolioserver.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, JpaSpecificationExecutor<Scrap> {

    Optional<Scrap> findByUserIdAndPostId(Long userId, Long postId);
    Long countByPostId(Long postId);
    Long countByUserIdAndPostId(Long userId, Long postId);
    @Query("SELECT COUNT(s.id) FROM Post p INNER JOIN Scrap s ON p.id = s.post.id WHERE p.user = :user")
    Long countByUser(@Param("user") User user);
    Page<Scrap> findAll(Specification<Scrap> spec, Pageable pageable);
    List<Scrap> findAll(Specification<Scrap> spec);

    @Transactional
    void deleteByPostId(Long postId);

}
