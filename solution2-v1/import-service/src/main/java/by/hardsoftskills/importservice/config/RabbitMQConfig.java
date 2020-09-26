package by.hardsoftskills.importservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "lab-results-exchange";
    public static final String ROUTING_KEY_LAB_RESULT_TYPE_PREFIX = "routing-lab-result-type-";
    private static final String QUEUE_LAB_RESULT_TYPE_PREFIX = "queue-lab-result-type-";

    @Value("${app.labResultType}")
    private String labResultType;

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_LAB_RESULT_TYPE_PREFIX + labResultType, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_LAB_RESULT_TYPE_PREFIX + labResultType);
    }
}
