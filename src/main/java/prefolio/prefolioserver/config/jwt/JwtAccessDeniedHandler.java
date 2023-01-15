package prefolio.prefolioserver.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        // 필요한 권한이 없이 접근하려 할 때 403
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // json 리턴 및 한글 깨짐 수정
        response.setContentType("application/json;charset=uft-8");
        JSONObject json = new JSONObject();
        String errorMsg = "접근 권한이 없습니다.";
        json.put("timestamp", LocalDateTime.now());
        json.put("code", "[403] FORBIDDEN");
        json.put("message", errorMsg);

        PrintWriter out = response.getWriter();
        out.print(json);
    }
}
