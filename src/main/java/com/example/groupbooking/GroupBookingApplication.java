package com.example.groupbooking;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GroupBookingApplication {
    
    public static void main(String[] args) {
        // 加载 .env 文件
        Dotenv dotenv = Dotenv.configure()
                .directory("./")  // .env 文件所在目录
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
        
        // 将 .env 中的变量设置为系统属性
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
        
        SpringApplication.run(GroupBookingApplication.class, args);
    }
}