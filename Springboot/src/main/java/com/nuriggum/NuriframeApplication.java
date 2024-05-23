package com.nuriggum;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NuriframeApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(NuriframeApplication.class);
		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);
	}

}
