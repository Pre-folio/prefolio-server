package prefolio.prefolioserver.domain.user.exception;

import prefolio.prefolioserver.global.error.BaseErrorException;

public class DataNotFound extends BaseErrorException {

    public static final DataNotFound EXCEPTION = new DataNotFound();

    private DataNotFound() {
        super(UserErrorCode.DATA_NOT_FOUND);
    }
}
