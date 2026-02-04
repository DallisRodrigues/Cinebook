package com.cinebook.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
	@Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
