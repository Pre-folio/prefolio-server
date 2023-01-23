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
import prefolio.prefolioserver.error.CustomException;
import prefolio.prefolioserver.service.JwtTokenService;

import java.io.IOException;

import static prefolio.prefolioserver.error.ErrorCode.INVALID_ACCESS_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenService jwtTokenService;

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
                throw new CustomException(INVALID_ACCESS_TOKEN);
            }
        }
        filterChain.doFilter(request, response);
    }

}
