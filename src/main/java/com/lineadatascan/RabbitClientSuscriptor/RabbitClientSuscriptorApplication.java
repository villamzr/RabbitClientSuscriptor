package com.lineadatascan.RabbitClientSuscriptor;

import java.net.Socket;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rabbitmq.client.ConnectionFactory;

//import com.rabbitmq.client.AMQP.PROTOCOL;

@SpringBootApplication
public class RabbitClientSuscriptorApplication<Routing> {

	static final String topicExchangeName = "spring-boot-exchange";

	static final String queueName = "spring-boot";
	
	static final String DEQUE_QUEUE_NAME = "dequeQueue";
	
	static RabbitTemplate RabbitTemplate = null;
	
	static Socket socket = new Socket();
	
	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}
	
	@Bean
	  public Queue dequeQueue() {
	      Queue queue = new Queue(DEQUE_QUEUE_NAME,true,false,false);
	      return queue;
	  }

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(dequeQueue()).to(exchange).with("spring-boot");
	}
	
	@Bean 
	ConnectionFactory factory() {
		return new ConnectionFactory();
	}
	
	
	@Bean
	public RabbitTemplate rabbitTemplate(final org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
	    RabbitTemplate = new RabbitTemplate(connectionFactory);
	    RabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
	    return RabbitTemplate;
	}
	 
	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
	    return new Jackson2JsonMessageConverter();
	}
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(RabbitClientSuscriptorApplication.class, args).close();
	}
}
