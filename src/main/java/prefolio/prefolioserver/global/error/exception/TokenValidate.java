package prefolio.prefolioserver.global.error.exception;

import prefolio.prefolioserver.global.error.BaseErrorException;
import prefolio.prefolioserver.global.error.GlobalErrorCode;

public class TokenValidate extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new TokenValidate();

    private TokenValidate() { super(GlobalErrorCode.INVALID_AUTH_TOKEN); }
}
