package gmky.core.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("unused")
public class InternalServerException extends BaseException {
    public InternalServerException(CoreExceptionEnum enumVal) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, enumVal);
    }

    public InternalServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, CoreExceptionEnum.INTERNAL_SERVER_ERROR);
    }
}
