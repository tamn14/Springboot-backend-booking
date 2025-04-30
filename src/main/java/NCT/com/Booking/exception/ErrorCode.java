package NCT.com.Booking.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    ROLES_EXISTED(1011, "Role existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    ROLE_NOT_EXISTED(1011, "Role not existed", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_EXISTED(1012, "Role not existed", HttpStatus.NOT_FOUND),
    PERMISSION_EXISTED(1013, "Permission existed", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    Flight_NOT_EXISTED(1009,"Flight not existed" ,  HttpStatus.NOT_FOUND),
    BOOKING_NOT_FOUND(1010,"Booking not existed" ,HttpStatus.NOT_FOUND ),
    ACCOUNTS_BLOCK(1015,"Account has been block" ,HttpStatus.UNAUTHORIZED ),
    INVALID_CREDENTIALS(1014,"password not true" ,HttpStatus.UNAUTHORIZED );

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
