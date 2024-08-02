package gmky.core.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("unused")
public class BadRequestException extends BaseException {
    public BadRequestException(CoreExceptionEnum enumVal) {
        super(HttpStatus.BAD_REQUEST, enumVal);
    }

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, CoreExceptionEnum.BAD_REQUEST);
    }
}
