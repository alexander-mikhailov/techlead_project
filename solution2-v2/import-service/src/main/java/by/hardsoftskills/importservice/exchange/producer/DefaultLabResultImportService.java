package by.hardsoftskills.importservice.exchange.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

import static by.hardsoftskills.importservice.config.RabbitMQConfig.EXCHANGE_NAME;
import static by.hardsoftskills.importservice.config.RabbitMQConfig.ROUTING_KEY_LAB_RESULT_TYPE_PREFIX;

@Component
public class DefaultLabResultImportService implements LabResultImportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLabResultImportService.class);

    private final RabbitTemplate rabbitTemplate;
    private final String labResultType;
    private boolean messageSentToQueue = true;

    public DefaultLabResultImportService(RabbitTemplate rabbitTemplate, @Value("${app.labResultType}") String labResultType) {
        this.rabbitTemplate = rabbitTemplate;
        this.labResultType = labResultType;
    }

    @PostConstruct
    private void initPublisherConfirmationCallback() {
        rabbitTemplate.setMandatory(true);

        // The ReturnCallback interface is used to implement the callback when a message is sent to the RabbitMQ exchange, but there is no corresponding queue bound to the exchange.
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            LOGGER.error("Message sending failed: No corresponding queue bound to exchange " + exchange);
            messageSentToQueue = false;
        });

        // The ConfirmCallback interface is used to receive ack callbacks after messages are sent to the RabbitMQ exchange.
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                LOGGER.error("Message sending failed: Message was not sent to RabbitMQ exchange - " + cause);
            } else if (messageSentToQueue) {
                LOGGER.info("Sending acknowledge to 3rd party service...");
            }
        });
    }

    @Override
    public void importLabResults() {
        LOGGER.info("Importing results from 3rd party service...");
        String labResult = UUID.randomUUID().toString();
        LOGGER.info(String.format("Sending lab result <%s> of type %s...", labResult, labResultType));
        messageSentToQueue = true;
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY_LAB_RESULT_TYPE_PREFIX + labResultType, labResult);
    }
}
