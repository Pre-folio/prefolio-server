package prefolio.prefolioserver.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import prefolio.prefolioserver.service.JwtTokenServiceImpl;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenServiceImpl jwtTokenService;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        String token = jwtTokenService.getToken((HttpServletRequest) request);
        if (token != null) {
            if (jwtTokenService.validateToken(token)) {
                Authentication authentication = jwtTokenService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new IOException("유효하지 않은 토큰입니다.");
            }
        }
        filterChain.doFilter(request, response);
    }

}
