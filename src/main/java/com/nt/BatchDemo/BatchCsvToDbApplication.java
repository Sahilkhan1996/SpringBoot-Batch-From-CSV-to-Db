package com.nt.BatchDemo;

import com.nt.BatchDemo.config.BatchCofig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BatchCsvToDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchCsvToDbApplication.class, args);

	}

}
