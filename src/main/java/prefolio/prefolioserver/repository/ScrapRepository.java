package prefolio.prefolioserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import prefolio.prefolioserver.domain.Scrap;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, JpaSpecificationExecutor<Scrap> {


    Optional<Scrap> findByUserIdAndPostId(Long userId, Long postId);
    Long countByPostId(Long postId);
    Optional<Long> countByUserId(Long userId);
    Page<Scrap> findAll(Specification<Scrap> spec, Pageable pageable);

}
