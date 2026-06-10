package com.finpay.user_service.exception;

/* Tại sao EXTENDS RuntimeException
Để có thể
throw new BusinessException(
    "Email already exists"
);
ở bất kỳ đâu
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message){
        super(message);
    }
}
