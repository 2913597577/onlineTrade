package com.situ.trade.shipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.situ.trade.shipservice",
		"com.situ.trade.commons.security"})
public class ShipServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipServiceApplication.class, args);
	}

}
