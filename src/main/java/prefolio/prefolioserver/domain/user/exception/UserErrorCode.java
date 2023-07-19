package prefolio.prefolioserver.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import prefolio.prefolioserver.global.common.dto.ErrorReason;
import prefolio.prefolioserver.global.error.BaseErrorCode;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    /* Admin */
    DUPLICATE_USER(BAD_REQUEST, "USER_400_1", "이미 가입한 어드민입니다."),
    INVALID_ACTION(BAD_REQUEST, "USER_400_2", "자기 자신에 대한 작업은 수행할 수 없습니다."),
    NOT_ALLOWED_TO_MODIFY(FORBIDDEN, "USER_403_1", "지원 기간에는 수정할 수 없습니다."),
    USER_NOT_SIGN_UP(NOT_FOUND, "USER_404_1", "회원으로 가입된 유저가 아닙니다"),
    USER_NOT_FOUND(NOT_FOUND, "USER_404_2", "존재하지 않는 어드민입니다."),

    /* Data */
    MISMATCH_CODE(BAD_REQUEST, "USER_400_3", "인증코드가 일치하지 않습니다"),
    MISMATCH_NEW_PASSWORD(BAD_REQUEST, "USER_400_3", "새비밀번호가 일치하지 않습니다"),
    MISMATCH_PASSWORD(BAD_REQUEST, "USER_400_4", "비밀번호가 일치하지 않습니다"),
    DATA_NOT_FOUND(CONFLICT, "USER_404_2", "존재하지 않는 데이터입니다"),
    DUPLICATE_DATA(CONFLICT, "USER_409_1", "이미 존재하는 데이터입니다"),

    /* REFRESH TOKEN */
    NOT_REFRESH_TOKEN(BAD_REQUEST, "USER_400_5", "리프레시 토큰이 아닙니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "USER_404_2", "존재하지 않거나 만료된 리프레시 토큰입니다.");
    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
