package com.rabbit.rabbitclientsuscriptor;

import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
	
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Enviando Mensaje...");
        final CustomMessage message = new CustomMessage("Holaaa aquiii", new Random().nextInt(50), false);
        RabbitClientSuscriptorApplication.RabbitTemplate.convertAndSend(RabbitClientSuscriptorApplication.topicExchangeName, "foo.bar.baz", message);
    }
}