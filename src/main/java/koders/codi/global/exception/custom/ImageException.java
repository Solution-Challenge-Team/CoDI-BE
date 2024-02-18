package koders.codi.global.exception.custom;

import koders.codi.global.exception.CommonException;
import koders.codi.global.exception.ErrorCode;

public class ImageException extends CommonException {
    public ImageException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
