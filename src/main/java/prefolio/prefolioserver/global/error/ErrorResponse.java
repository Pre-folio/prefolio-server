package prefolio.prefolioserver.global.error;

import lombok.Builder;
import lombok.Getter;
import prefolio.prefolioserver.global.common.dto.ErrorReason;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ErrorResponse {
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    private final boolean success = false;
    private String code;
    private String status;
    private String reason;

    @Builder
    private ErrorResponse(String code, String status, String reason) {
        this.code = code;
        this.status = status;
        this.reason = reason;
    }

    public static ErrorResponse from(ErrorReason errorReason) {
        return ErrorResponse.builder()
                .code(errorReason.getCode())
                .status(errorReason.getStatus().toString())
                .reason(errorReason.getReason())
                .build();
    }
}
