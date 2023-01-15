package prefolio.prefolioserver.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface JwtTokenService {
    public String getToken(HttpServletRequest request);

    public Boolean validateToken(String token);

    public Authentication getAuthentication(String token);

    public Long getUserIdFromJwtToken(String token);
}
