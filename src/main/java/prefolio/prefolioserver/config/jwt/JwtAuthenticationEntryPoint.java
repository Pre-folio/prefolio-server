package prefolio.prefolioserver.config.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/** 인증되지 않았을 경우(비로그인) AuthenticationEntryPoint 부분에서 AuthenticationException 발생시키면서
 * 비로그인 상태에서 인증 실패 시, AuthenticationEntryPoint로 핸들링되어 이곳에서 처리 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e
    ) throws IOException {

        // status를 401 에러로 지정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // json 리턴 및 한글 깨짐 수정
        response.setContentType("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        String errorMsg = "잘못된 접근입니다.";
        json.put("timestamp", LocalDateTime.now());
        json.put("code", "[401] UNAUTHORIZED");
        json.put("message", errorMsg);

        PrintWriter out = response.getWriter();
        out.print(json);
    }
}


