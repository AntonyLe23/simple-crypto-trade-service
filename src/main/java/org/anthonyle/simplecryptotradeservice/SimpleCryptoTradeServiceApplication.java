package org.anthonyle.simplecryptotradeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimpleCryptoTradeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleCryptoTradeServiceApplication.class, args);
	}

}

