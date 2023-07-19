package prefolio.prefolioserver.domain.post.exception;

import prefolio.prefolioserver.global.error.BaseErrorException;

public class PostNotFound extends BaseErrorException {

    public static final PostNotFound EXCEPTION = new PostNotFound();

    private PostNotFound() {
        super(PostErrorCode.DATA_NOT_FOUND);
    }
}
