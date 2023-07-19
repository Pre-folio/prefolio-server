package prefolio.prefolioserver.domain.post.exception;

import prefolio.prefolioserver.global.error.BaseErrorException;

public class DataNotFound extends BaseErrorException {

    public static final DataNotFound EXCEPTION = new DataNotFound();

    private DataNotFound() {
        super(PostErrorCode.DATA_NOT_FOUND);
    }
}
