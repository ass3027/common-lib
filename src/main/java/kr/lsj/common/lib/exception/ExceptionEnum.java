package kr.lsj.common.lib.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ExceptionEnum {

    NO_SELECTED         (HttpStatus.NOT_FOUND,      "0 rows selected"),
    NO_INSERTED         (HttpStatus.NOT_FOUND,      "0 rows inserted"),
    NO_UPDATED          (HttpStatus.NOT_FOUND,      "0 rows updated"),
    NO_DELETED          (HttpStatus.NOT_FOUND,      "0 rows deleted"),
    ACCESS_DENIED       (HttpStatus.FORBIDDEN,      "Access denied"),
    NO_USER_WITH_ID     (HttpStatus.UNAUTHORIZED,   "No User with ID"),
    DISABLED_USER       (HttpStatus.UNAUTHORIZED,   "User is disabled"),
    INCORRECT_PASSWORD  (HttpStatus.UNAUTHORIZED,   "Incorrect password"),;

    public final HttpStatus httpStatus;
    public final String message;

}
