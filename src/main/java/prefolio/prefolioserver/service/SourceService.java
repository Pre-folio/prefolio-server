package prefolio.prefolioserver.service;

import org.springframework.stereotype.Service;

@Service
public interface SourceService {
    String createURL(UserDetailsImpl authUser, String filePath);
}
