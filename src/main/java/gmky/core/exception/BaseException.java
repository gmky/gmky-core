package gmky.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
@SuppressWarnings("unused")
public class BaseException extends ResponseStatusException {
    private final String code;

    public BaseException(HttpStatus status, String code, String reason) {
        super(status, reason);
        this.code = code;
    }

    public BaseException(HttpStatus status, CoreExceptionEnum enumVal) {
        super(status, enumVal.getMessage());
        this.code = enumVal.getCode();
    }
}
