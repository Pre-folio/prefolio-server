package prefolio.prefolioserver.domain.user.exception;


import prefolio.prefolioserver.global.error.BaseErrorException;

public class DuplicateData extends BaseErrorException {

    public static final DuplicateData EXCEPTION = new DuplicateData();

    private DuplicateData() {
        super(UserErrorCode.DUPLICATE_DATA);
    }
}
