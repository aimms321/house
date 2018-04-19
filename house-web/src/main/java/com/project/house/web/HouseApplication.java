package com.project.house.web;
//import com.mooc1.house.autoconfig.EnableHttpClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.project.house.*")
@MapperScan("com.project.house.*")
//@EnableHttpClient
@EnableAsync
@ServletComponentScan(basePackages = "com.project.house")

public class HouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseApplication.class, args);
	}
}
