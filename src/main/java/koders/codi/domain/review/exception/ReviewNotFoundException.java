package koders.codi.domain.review.exception;

import koders.codi.global.exception.CommonException;
import koders.codi.global.exception.ErrorCode;

public class ReviewNotFoundException extends CommonException {
    public ReviewNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
