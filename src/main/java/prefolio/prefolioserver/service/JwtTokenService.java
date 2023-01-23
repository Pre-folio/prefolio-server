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
import prefolio.prefolioserver.error.CustomException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import static prefolio.prefolioserver.error.ErrorCode.INVALID_ACCESS_TOKEN;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final UserDetailsServiceImpl userDetailsService;
    @Value("${jwt.secret}")
    private String JWT_SECRET;


    public String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null) {
            String accessToken = bearerToken.substring(bearerToken.lastIndexOf(" ")+1);
            return accessToken;
        }
        return bearerToken;
    }


    public Boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(
                            StandardCharsets.UTF_8))).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            throw new CustomException(INVALID_ACCESS_TOKEN);
        }
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
