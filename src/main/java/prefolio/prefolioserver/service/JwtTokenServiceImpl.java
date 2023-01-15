package prefolio.prefolioserver.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final UserDetailsServiceImpl userDetailsService;
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Override
    public String getToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    @Override
    public Boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(
                            StandardCharsets.UTF_8))).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                this.getUserIdFromJwtToken(token).toString());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public Long getUserIdFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(
                        StandardCharsets.UTF_8)))
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
}
