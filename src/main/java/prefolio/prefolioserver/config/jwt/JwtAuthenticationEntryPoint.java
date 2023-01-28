package prefolio.prefolioserver.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import prefolio.prefolioserver.error.ErrorCode;
import prefolio.prefolioserver.error.ErrorResponse;

import java.io.IOException;



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
        String exception = (String)request.getAttribute("exception");

        if(exception == null || exception.equals(ErrorCode.NO_TOKEN.getCode())) {
            setResponse(response, ErrorCode.NO_TOKEN);
        }
//        //유효하지 않은 토큰인 경우
//        else if(exception.equals(ErrorCode.INVALID_ACCESS_TOKEN.getCode())) {
//            setResponse(response, ErrorCode.INVALID_ACCESS_TOKEN);
//        }
        //잘못된 서명
        else if(exception.equals(ErrorCode.INVALID_SIGNATURE.getCode())) {
            setResponse(response, ErrorCode.INVALID_SIGNATURE);
        }
        //토큰 만료된 경우
        else if(exception.equals(ErrorCode.EXPIRED_TOKEN.getCode())) {
            setResponse(response, ErrorCode.EXPIRED_TOKEN);
        }
        //지원되지 않는 토큰인 경우
        else if(exception.equals(ErrorCode.UNSUPPORTED_TOKEN.getCode())) {
            setResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
        }
//        //잘못된 타입의 토큰인 경우
//        else if(exception.equals(ErrorCode.INVALID_AUTH_TOKEN.getCode())) {
//            setResponse(response, ErrorCode.INVALID_AUTH_TOKEN);
//        }
//        //DB에 정보가 없는 토큰을 사용했울 때
//        else if(exception.equals(ErrorCode.INVALID_USER_TOKEN.getCode())) {
//            setResponse(response, ErrorCode.INVALID_USER_TOKEN);
//        }
//        else {
//            setResponse(response, ErrorCode.FORBIDDEN_USER);
//        }
    }

    /**
     *  스프링 시큐리티 예외 커스텀 함수
     */
    public void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(ErrorResponse.jsonOf(errorCode));
    }

}


