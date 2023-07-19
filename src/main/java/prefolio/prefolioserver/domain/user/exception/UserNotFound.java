package prefolio.prefolioserver.domain.user.exception;

import prefolio.prefolioserver.global.error.BaseErrorException;

public class UserNotFound extends BaseErrorException {

    public static final UserNotFound EXCEPTION = new UserNotFound();

    private UserNotFound() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
