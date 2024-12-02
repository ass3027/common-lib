package kr.lsj.common.lib.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenEmptyException extends RuntimeException {
    private final String message;
}
