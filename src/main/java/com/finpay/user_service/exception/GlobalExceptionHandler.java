package com.finpay.user_service.exception;

import com.finpay.user_service.model.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice // Đánh dấu đây là bộ interceptor bắt lỗi toàn cục cho các Controller
public class GlobalExceptionHandler {
    // chú ý: ResponseEntity là một class Spring dùng để đại diện cho toàn bộ HTTP Response
    // 1. Bắt lỗi BadRequestException và trả về HTTP Status 400 (Bad Request)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequestException(BadRequestException ex){
        ApiResponse<Object> response = new ApiResponse<>("ERROR_BAD_REQUEST",ex.getMessage(),null);
        return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    // 2. Bắt lỗi ResourceNotFoundException và trả về HTTP Status 404 (Not Found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex){
        ApiResponse<Object> response = new ApiResponse<>("ERROR_NOT_FOUND",ex.getMessage(),null);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    //3. Lưới bọc cuối cùng : Bắt all các lỗi hệ thống không lường trước được (NullPointer, DB sập...)
    public  ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex){
        //Trên môi trường thật, ta log lại lỗi này để dev vào sửa: ẽ.printStackTrace()
        log.error("Unexpected error", ex);
        ApiResponse<Object> response = new ApiResponse<>("INTERNAL_SERVER_ERROR", "An unexpected error occurred",null);
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
