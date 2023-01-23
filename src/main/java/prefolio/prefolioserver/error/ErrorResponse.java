package prefolio.prefolioserver.error;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.getCode())
                        .message(errorCode.getDetail())
                        .build()
                );
    }

    public static JSONObject jsonOf(ErrorCode errorCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timestamp", LocalDateTime.now());
        jsonObject.put("success", false);
        jsonObject.put("status", errorCode.getHttpStatus().value());
        jsonObject.put("code", errorCode.getCode());
        jsonObject.put("message", errorCode.getDetail());

        return jsonObject;
    }

    public static void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(ErrorResponse.jsonOf(errorCode));
    }
}