package prefolio.prefolioserver.domain.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 공통 에러 */
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "E000", "서버 에러, 관리자에게 문의 바랍니다"),
    _BAD_REQUEST(BAD_REQUEST, "E001", "잘못된 요청입니다"),
    _UNAUTHORIZED(UNAUTHORIZED, "E002", "권한이 없습니다"),
    _INVALID_REQUEST_PARAMETER(BAD_REQUEST, "E003", "유효하지 않은 Request Parameter 입니다"),
    INVALID_REQUEST_PART(BAD_REQUEST, "E004", "올바르지 않은 파라미터 형식입니다"),

    /* DB 에러 */
    CANNOT_CREATE_TUPLE(INTERNAL_SERVER_ERROR, "DB000", "새로운 인스턴스 생성을 실패했습니다"),
    CANNOT_UPDATE_TUPLE(INTERNAL_SERVER_ERROR, "DB001", "인스턴스 업데이트에 실패했습니다"),

    /* Auth 에러 */
    NO_TOKEN(UNAUTHORIZED, "AUTH000", "토큰이 존재하지 않습니다"),
    EXPIRED_TOKEN(UNAUTHORIZED, "AUTH001", "만료된 엑세스 토큰입니다"),
    INVALID_REFRESH_TOKEN(UNAUTHORIZED, "AUTH002", "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(UNAUTHORIZED, "AUTH003", "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    EXPIRED_REFRESH_TOKEN(UNAUTHORIZED, "AUTH004", "만료된 리프레시 토큰입니다"),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "AUTH005", "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_USER(UNAUTHORIZED, "AUTH006", "로그인이 필요합니다"),
    REFRESH_TOKEN_NOT_FOUND(UNAUTHORIZED, "AUTH007", "로그아웃 된 사용자입니다"),
    FORBIDDEN_USER(FORBIDDEN, "AUTH008", "권한이 없는 유저입니다"),
    UNSUPPORTED_TOKEN(UNAUTHORIZED, "AUTH009", "지원하지 않는 토큰입니다"),
    INVALID_SIGNATURE(UNAUTHORIZED, "AUTH010", "잘못된 JWT 서명입니다"),
    /* Kakao */
    MISMATCH_VERIFICATION_CODE(UNAUTHORIZED, "AUTH011", "인가코드가 일치하지 않습니다"),
    EXPIRED_VERIFICATION_CODE(UNAUTHORIZED, "AUTH012", "인가코드가 만료되었습니다"),
    INVALID_USER_TOKEN(UNAUTHORIZED, "AUTH013", "서버에 토큰과 일치하는 정보가 없습니다"),

    LOGIN_FAILED(UNAUTHORIZED, "AUTH014", "로그인에 실패했습니다"),
    INVALID_ACCESS_TOKEN(UNAUTHORIZED, "AUTH015", "유효하지 않은 엑세스 토큰입니다"),


    /* User 에러 */
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "U001", "자기 자신은 팔로우 할 수 없습니다"),
    USER_ALREADY_EXIST(BAD_REQUEST, "U002","이미 가입된 유저입니다"),
    USER_NOT_FOUND(NOT_FOUND, "U003","해당 유저 정보를 찾을 수 없습니다"),
    USER_NOT_SIGNED_UP(NOT_FOUND, "U004","회원으로 가입된 유저가 아닙니다"),
    USER_ALREADY_LOGGED_IN(BAD_REQUEST, "U005","이미 로그인 상태입니다"),
    USER_ALREADY_LOGGED_OUT(BAD_REQUEST, "U06","이미 로그아웃 상태입니다"),
    
    /* Post 에러 */
    CANNOT_SCRAP_MY_POST(BAD_REQUEST, "P001", "자기 자신의 글을 스크랩 할 수 없습니다."),
    CANNOT_LIKE_MY_POST(BAD_REQUEST, "P001", "자기 자신의 글을 좋아요 할 수 없습니다."),
    POST_NOT_FOUND(NOT_FOUND, "P003", "해당 글 정보를 찾을 수 없습니다."),


    /* Validation 에러 */
    PARAMETER_NOT_VALID(BAD_REQUEST, "P000", "인자가 유효하지 않습니다"),

    /* Database 에러 */
    DUPLICATE_RESOURCE(CONFLICT, "D001", "데이터가 이미 존재합니다"),
    DATA_NOT_FOUND(NOT_FOUND, "D002", "데이터가 존재하지 않습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String detail;
}