package by.hardsoftskills.importservice.exchange.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static by.hardsoftskills.importservice.config.RabbitMQConfig.EXCHANGE_NAME;
import static by.hardsoftskills.importservice.config.RabbitMQConfig.ROUTING_KEY_LAB_RESULT_TYPE_PREFIX;

@Component
public class DefaultLabResultImportService implements LabResultImportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLabResultImportService.class);

    private final AmqpTemplate amqpTemplate;
    private final String labResultType;

    public DefaultLabResultImportService(AmqpTemplate amqpTemplate, @Value("${app.labResultType}") String labResultType) {
        this.amqpTemplate = amqpTemplate;
        this.labResultType = labResultType;
    }

    public void importLabResults() {
        LOGGER.info("Importing results from 3rd party service...");
        String labResult = UUID.randomUUID().toString();
        LOGGER.info(String.format("Sending lab result <%s> of type %s to message broker...", labResult, labResultType));
        amqpTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY_LAB_RESULT_TYPE_PREFIX + labResultType, labResult);
        LOGGER.info("Sending acknowledge to 3rd party service...");
    }
}
