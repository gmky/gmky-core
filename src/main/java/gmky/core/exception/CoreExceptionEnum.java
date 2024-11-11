package gmky.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoreExceptionEnum {
    FORBIDDEN("G-0001", "Forbidden"),
    NOT_FOUND("G-0004", "Not Found"),
    BAD_REQUEST("G-0002", "Bad Request"),
    UNAUTHORIZED("G-0003", "Unauthorized"),
    INTERNAL_SERVER_ERROR("G-0005", "Internal Server Error"),
    PERMISSION_NOT_FOUND("G-0006", "Permission Not Found"),
    PERMISSION_SET_NOT_FOUND("G-0007", "Permission Set Not Found"),
    ROLE_NOT_FOUND("G-0008", "Role Not Found"),
    PROFILE_NOT_FOUND("G-0009", "Profile Not Found"),
    INVALID_PERMISSION_SET("G-0010", "Invalid Permission Set"),
    PERMISSION_SET_NOT_EMPTY("G-0011", "Permission Set Not Empty"),
    ROLE_NOT_EMPTY("G-0012", "Role Not Empty"),
    ROLE_NAME_EXISTED("G-0013", "Role Name Existed"),
    PERMISSION_SET_NAME_EXISTED("G-0014", "Permission Set Name Existed"),
    UNDER_MAINTENANCE("G-0015", "Feature under maintenance"),
    USERNAME_EXISTED("G-0016", "Username existed"),
    ;

    private final String code;
    private final String message;
}
