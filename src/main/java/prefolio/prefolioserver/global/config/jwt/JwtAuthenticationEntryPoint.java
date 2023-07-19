package prefolio.prefolioserver.global.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import prefolio.prefolioserver.global.error.BaseErrorCode;
import prefolio.prefolioserver.global.error.ErrorResponse;
import prefolio.prefolioserver.global.error.exception.NoToken;

import java.io.IOException;


/**
 * 인증되지 않았을 경우(비로그인) AuthenticationEntryPoint 부분에서 AuthenticationException 발생시키면서
 * 비로그인 상태에서 인증 실패 시, AuthenticationEntryPoint로 핸들링되어 이곳에서 처리
 */
@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e
    ) throws IOException {
        responseToClient(response, getErrorResponse(NoToken.EXCEPTION.getErrorCode()));
    }

    /**
     * 스프링 시큐리티 예외 커스텀 함수
     */

    private ErrorResponse getErrorResponse(BaseErrorCode errorCode) {

        return ErrorResponse.from(errorCode.getErrorReason());
    }
    private void responseToClient(HttpServletResponse response, ErrorResponse errorResponse)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(Integer.parseInt(errorResponse.getStatus()));
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}


