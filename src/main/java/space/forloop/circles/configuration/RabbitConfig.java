package space.forloop.circles.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author Chris Turner (chris@forloop.space) */
@Configuration
public class RabbitConfig {

  private static final String TOPIC_EXCHANGE_NAME = "circle-service-exchange";

  private static final String ROUTING_KEY_NAME = "circleId.#";

  private static final String QUEUE_NAME = "new-posts";

  @Bean
  public Queue queue() {
    return new Queue(QUEUE_NAME, false);
  }

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(TOPIC_EXCHANGE_NAME);
  }

  @Bean
  public Binding binding(final Queue queue, final TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_NAME);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());

    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
