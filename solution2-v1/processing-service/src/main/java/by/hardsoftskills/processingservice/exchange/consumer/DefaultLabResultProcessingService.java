package by.hardsoftskills.processingservice.exchange.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultLabResultProcessingService implements LabResultProcessingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLabResultProcessingService.class);

    private final long processingTime;

    public DefaultLabResultProcessingService(@Value("${app.labResultProcessingTime}") long processingTime) {
        this.processingTime = processingTime;
    }

    public void processLabResult(String labResult) {
        LOGGER.info(String.format("Processing lab result <%s>", labResult));
        try {
            Thread.sleep(processingTime);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
