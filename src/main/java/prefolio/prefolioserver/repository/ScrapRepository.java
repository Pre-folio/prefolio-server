package prefolio.prefolioserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefolio.prefolioserver.domain.Scrap;
import prefolio.prefolioserver.domain.User;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {


    public Optional<Scrap> findByUserIdAndPostId(Long userId, Long postId);
    public Long countByPostId(Long postId);
    public Optional<Long> countByUserId(Long userId);
    //public Optional<Scrap> findAllByUserId(Long userId);

    public List<Scrap> findAllByUserId(Long userId);

}
