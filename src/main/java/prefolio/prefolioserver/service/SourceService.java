package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.constant.Path;
import prefolio.prefolioserver.dto.response.GetPathResponseDTO;

@Service
public interface SourceService {

    GetPathResponseDTO createURL(UserDetailsImpl authUser, Path path);
}
