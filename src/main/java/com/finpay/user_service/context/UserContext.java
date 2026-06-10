package com.finpay.user_service.context;

public class UserContext {
    /*
    ThreadLocal  mỗi thread là 1 vùng nhớ riêng
     */
    private static final ThreadLocal<String> email = new ThreadLocal<>();
    private  static  final ThreadLocal<String> role = new ThreadLocal<>();
    public static void setEmail(String value){
        email.set(value);
    }
    public static String getEmail(){
        return email.get();
    }
    public static void setRole(String value){
        role.set(value);
    }
    public static String getRole(){
        return role.get();
    }
    public static void clear() {
        email.remove();
        role.remove();
    }
}
