package gmky.core.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {
    public ForbiddenException(CoreExceptionEnum enumVal) {
        super(HttpStatus.FORBIDDEN, enumVal);
    }

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, CoreExceptionEnum.FORBIDDEN);
    }
}
