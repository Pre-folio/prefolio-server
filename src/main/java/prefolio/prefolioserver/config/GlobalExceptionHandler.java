package prefolio.prefolioserver.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import prefolio.prefolioserver.domain.User;
import prefolio.prefolioserver.error.CustomException;
import prefolio.prefolioserver.error.ErrorResponse;

import static prefolio.prefolioserver.error.ErrorCode.DUPLICATE_RESOURCE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleDataException() {
        log.error("handleDataException throw Exception : {}", DUPLICATE_RESOURCE);
        return ErrorResponse.toResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
