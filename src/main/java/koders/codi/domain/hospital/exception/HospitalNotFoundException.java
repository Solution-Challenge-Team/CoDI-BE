package koders.codi.domain.hospital.exception;

import koders.codi.global.exception.CommonException;
import koders.codi.global.exception.ErrorCode;

public class HospitalNotFoundException extends CommonException {
    public HospitalNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
