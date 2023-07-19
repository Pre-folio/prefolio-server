package prefolio.prefolioserver.domain.user.exception;

import prefolio.prefolioserver.global.error.BaseErrorException;

public class MismatchCode extends BaseErrorException {

    public static final MismatchCode EXCEPTION = new MismatchCode();

    private MismatchCode() {
        super(UserErrorCode.MISMATCH_CODE);
    }
}
