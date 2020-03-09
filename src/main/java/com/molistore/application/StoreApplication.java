package com.molistore.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableCaching
@EnableScheduling
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class})
@SpringBootApplication(exclude = {LiquibaseAutoConfiguration.class, JacksonAutoConfiguration.class})
public class StoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(StoreApplication.class, args);
  }

}
