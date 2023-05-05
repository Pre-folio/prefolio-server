package prefolio.prefolioserver.service;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.User;
import prefolio.prefolioserver.error.CustomException;
import prefolio.prefolioserver.error.ErrorCode;
import prefolio.prefolioserver.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static prefolio.prefolioserver.error.ErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
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
        } catch (SignatureException | MalformedJwtException ex) {
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

    public Long getUserIdFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(
                        StandardCharsets.UTF_8)))
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    // JWT 토큰 리턴
    public List<String> usersAuthorizationInput(User user) {
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(String.valueOf(user.getId()));
        String accessToken = generateJwtAccessToken(userDetails);
        String refreshToken = generateJwtRefreshToken(userDetails);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        List<String> token = new ArrayList<>();
        token.add(accessToken);
        token.add(refreshToken);

        return token;
    }

//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(
//                this.getUserIdFromJwtToken(token)));
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }

    // 로그인 처리
    public Authentication getAuthentication(String token) {
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(String.valueOf(
                getUserIdFromJwtToken(token)));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }


    public String generateJwtAccessToken(UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 3);  // 만료시간 1시간

        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn = new Date(cal.getTimeInMillis());

        return buildAccessToken(user.getId(), issuedAt, accessTokenExpiresIn);
    }

    public String generateJwtRefreshToken(UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 14);  // 만료일 14일

        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn = new Date(cal.getTimeInMillis());

        return buildAccessToken(user.getId(), issuedAt, accessTokenExpiresIn);
    }

    public String buildAccessToken(Long id, Date issuedAt, Date accessTokenExpiresIn) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(id.toString())
                .setIssuer("prefolio")
                .setIssuedAt(issuedAt)
                .setExpiration(accessTokenExpiresIn)
                .claim("id", id)
                .claim("roles", "USER")
                .signWith(
                        SignatureAlgorithm.HS256,
                        Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(StandardCharsets.UTF_8))
                )
                .compact();
    }
}
