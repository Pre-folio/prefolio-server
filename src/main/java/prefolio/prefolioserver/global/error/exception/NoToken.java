package prefolio.prefolioserver.global.error.exception;

import prefolio.prefolioserver.global.error.BaseErrorException;
import prefolio.prefolioserver.global.error.GlobalErrorCode;

public class NoToken extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new NoToken();

    private NoToken() { super(GlobalErrorCode.NO_TOKEN); }
}
