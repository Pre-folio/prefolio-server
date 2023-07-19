package prefolio.prefolioserver.global.error.exception;

import prefolio.prefolioserver.global.error.BaseErrorException;
import prefolio.prefolioserver.global.error.GlobalErrorCode;

public class NotRefreshToken extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new NotRefreshToken();

    private NotRefreshToken() { super(GlobalErrorCode.NO_TOKEN); }
}
