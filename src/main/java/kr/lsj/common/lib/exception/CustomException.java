package kr.lsj.common.lib.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CustomException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(ExceptionEnum exceptionEnum) {
        this(exceptionEnum.message, exceptionEnum.httpStatus);
    }

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ResponseEntity<String> getResponseEntityWithMessage() {
        return ResponseEntity.status(httpStatus).body(message);
    }
}
