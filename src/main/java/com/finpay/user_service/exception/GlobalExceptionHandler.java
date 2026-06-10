package com.finpay.user_service.exception;

import com.finpay.user_service.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/*
*
Nó giống
try{

}catch(){
}
*
* Nhưng áp dụng cho toàn bộ project
* */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
    Nếu trong Contr or Service ném ra MethodArgumentNotValidException thì gọi meth này
     */
    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        String message =
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();

        return new ErrorResponse(message);
    }

    /*
    Nếu trong Contr or Service ném ra BusinessEx thì gọi meth này
     */
    @ExceptionHandler(BusinessException.class)
    /*
    staus có thể cấu hình tùy ý nếu có trong httpstatus
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleBusinessException(BusinessException ex){
        return new ErrorResponse(ex.getMessage());
    }
}
