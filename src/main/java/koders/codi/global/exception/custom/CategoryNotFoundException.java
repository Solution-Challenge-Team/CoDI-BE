package koders.codi.global.exception.custom;

import koders.codi.global.exception.CommonException;
import koders.codi.global.exception.ErrorCode;

public class CategoryNotFoundException extends CommonException {
    public CategoryNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
