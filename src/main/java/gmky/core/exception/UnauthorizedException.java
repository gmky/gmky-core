package gmky.core.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("unused")
public class UnauthorizedException extends BaseException {
    public UnauthorizedException(CoreExceptionEnum enumVal) {
        super(HttpStatus.UNAUTHORIZED, enumVal);
    }

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, CoreExceptionEnum.UNAUTHORIZED);
    }
}
