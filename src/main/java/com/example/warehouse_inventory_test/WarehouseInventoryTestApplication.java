package com.example.warehouse_inventory_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WarehouseInventoryTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseInventoryTestApplication.class, args);
	}

}
