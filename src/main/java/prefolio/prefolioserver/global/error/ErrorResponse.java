package prefolio.prefolioserver.global.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;
    private final List<ErrorField> errors;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.getCode())
                        .message(errorCode.getDetail())
                        .errors(List.of())
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

    public static JSONObject jsonOf(ErrorCode errorCode, CustomException exception) {
        //BindingResult bindingResult = exception.getBindingResult();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timestamp", LocalDateTime.now());
        jsonObject.put("success", false);
        jsonObject.put("status", errorCode.getHttpStatus().value());
        jsonObject.put("code", errorCode.getCode());
        jsonObject.put("errors", exception.getStackTrace());
        jsonObject.put("message", errorCode.getDetail());

        return jsonObject;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorField {
        private String field;

        private String value;

        private String reason;

        public static List<ErrorField> of(BindingResult bindingResult) {
            try {
                List<ErrorField> errorFields =
                        bindingResult.getAllErrors().stream().map(error ->
                        {
                            FieldError fieldError = (FieldError) error;

                            return new ErrorField(
                                    fieldError.getField(),
                                    Objects.toString(fieldError.getRejectedValue()),
                                    fieldError.getDefaultMessage());
                        }).collect(Collectors.toList());
                return errorFields;
            } catch (Exception e) {
                System.out.println("e = " + e);
                return Arrays.asList();
            }
        }
    }
}