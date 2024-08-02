package gmky.core.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("unused")
public class NotFoundException extends BaseException {
    public NotFoundException(CoreExceptionEnum enumVal) {
        super(HttpStatus.NOT_FOUND, enumVal);
    }

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, CoreExceptionEnum.NOT_FOUND);
    }
}
