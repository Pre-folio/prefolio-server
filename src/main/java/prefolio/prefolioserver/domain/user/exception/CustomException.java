package prefolio.prefolioserver.domain.user.exception;

import lombok.Getter;

@Getter
//@AllArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
    private Exception exception;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, Exception exception) {
        this.errorCode = errorCode;
        this.exception = exception;
    }

}