package com.dev.sharing.credit.service;

import com.dev.sharing.common.api.swagger.EnableCommonOpenAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonOpenAPI
public class CreditServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CreditServiceApplication.class, args);
  }

}
