package com.finpay.user_service.model;

import java.time.LocalDateTime;

public class ApiResponse<T>{
    private String status;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // Getters và Setters (Bạn có thể tự tạo tự động bằng IntelliJ bằng tổ hợp phím Alt+Insert)
    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public Object getData() { return data; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
