package com.finpay.user_service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.TimeZone;
@SpringBootApplication
@EnableFeignClients //KÍCH HOẠT OPENFEIGN
public class UserServiceApplication {
	public static void main(String[] args) {
		System.out.println("Timezone = "
				+ java.util.TimeZone.getDefault().getID());
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
