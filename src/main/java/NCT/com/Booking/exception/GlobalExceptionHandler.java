package NCT.com.Booking.exception;

import NCT.com.Booking.DTO.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.validation.ConstraintViolation;

import java.util.Map;
import java.util.Objects;
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min" ;
    // chuyen doi cac gia tri de dua vao thong bao loi validation
    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
    // Exception bat cac loi co the bi bo xot
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException (RuntimeException runtimeException) {
        log.error("Exception : " , runtimeException);
        ApiResponse apiResponse = new ApiResponse() ;
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMess(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
    // bat loi dang nhap khong dung username hoac mat khau
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentials(BadCredentialsException ex) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED ;
        return ResponseEntity.status(errorCode.getStatusCode()) // tuy chinh status code
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .mess(ErrorCode.INVALID_CREDENTIALS.getMessage())
                        .build()
                );
    }
    // bat loi tai khoan dang bi khoa
    @ExceptionHandler(value = DisabledException.class)
    public ResponseEntity<ApiResponse> handleDisabled(DisabledException ex) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED ;
        return ResponseEntity.status(errorCode.getStatusCode()) // tuy chinh status code
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .mess(ErrorCode.ACCOUNTS_BLOCK.getMessage())
                        .build()
                );
    }
    // bat cac loi dang nhap khong co quyen
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException accessDeniedException) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED ;
        return ResponseEntity.status(errorCode.getStatusCode()) // tuy chinh status code
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .mess(errorCode.getMessage())
                        .build()
                );
    }

    // bat loi khi co loi xuat hien
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException appException) {
        ErrorCode errorCode = appException.getErrorCode();
        ApiResponse apiResponse = new ApiResponse() ;
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMess(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);

    }

    // bat loi validation
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);

            var constraintViolation =
                    exception.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());

        } catch (IllegalArgumentException e) {

        }

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMess(
                Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMessage(), attributes)
                        : errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }







}
