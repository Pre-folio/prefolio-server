package prefolio.prefolioserver.global.error.exception;

import prefolio.prefolioserver.global.error.BaseErrorException;
import prefolio.prefolioserver.global.error.GlobalErrorCode;

public class ForbiddenAdmin extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new ForbiddenAdmin();

    private ForbiddenAdmin() { super(GlobalErrorCode.FORBIDDEN_USER); }
}
