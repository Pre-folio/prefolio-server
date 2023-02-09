package prefolio.prefolioserver.config.jwt;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import prefolio.prefolioserver.error.CustomException;
import prefolio.prefolioserver.error.ErrorCode;
import prefolio.prefolioserver.error.ErrorResponse;
import prefolio.prefolioserver.service.JwtTokenService;

import java.io.IOException;

import static prefolio.prefolioserver.error.ErrorCode.INVALID_ACCESS_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        String token = jwtTokenService.getAccessToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (StringUtils.isNotBlank(token) && jwtTokenService.validateToken(request, token)) {
            Authentication authentication = jwtTokenService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }


}
