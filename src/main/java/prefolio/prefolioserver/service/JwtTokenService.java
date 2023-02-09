package prefolio.prefolioserver.service;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.error.CustomException;
import prefolio.prefolioserver.error.ErrorCode;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import static prefolio.prefolioserver.error.ErrorCode.INVALID_ACCESS_TOKEN;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final UserDetailsServiceImpl userDetailsService;
    @Value("${jwt.secret}")
    private String JWT_SECRET;


    public String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String accessToken = bearerToken.substring("Bearer ".length());
            return accessToken;
        }
        return bearerToken;
    }

    public Boolean validateToken(HttpServletRequest request, String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(
                            StandardCharsets.UTF_8))).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException ex) {
            log.error("잘못된 JWT 서명입니다");
            request.setAttribute("exception", ErrorCode.INVALID_SIGNATURE.getCode());
        } catch (ExpiredJwtException ex) {
            log.error("만료된 JWT 토큰입니다");
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getCode());
        } catch (UnsupportedJwtException ex) {
            log.error("지원하지 않는 JWT 토큰입니다.");
            request.setAttribute("exception", ErrorCode.UNSUPPORTED_TOKEN.getCode());
        } catch (IllegalArgumentException ex) {
            log.error("JWT 토큰이 비어있습니다");
            request.setAttribute("exception", ErrorCode.NO_TOKEN.getCode());
        }
        return false;
    }


    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(
                this.getUserIdFromJwtToken(token)));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    public Long getUserIdFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(
                        StandardCharsets.UTF_8)))
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
}
