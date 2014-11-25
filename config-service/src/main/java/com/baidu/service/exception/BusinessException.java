package com.baidu.service.exception;

/**
 * Created by edwardsbean on 14-11-18.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
