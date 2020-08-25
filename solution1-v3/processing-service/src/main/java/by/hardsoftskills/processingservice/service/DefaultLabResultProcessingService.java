package by.hardsoftskills.processingservice.service;

import by.hardsoftskills.processingservice.integration.importservice.client.api.exchange.Result;
import by.hardsoftskills.processingservice.integration.importservice.client.ImportServiceClient;
import by.hardsoftskills.processingservice.service.processor.ResultProcessor;
import by.hardsoftskills.processingservice.service.processor.context.ResultProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DefaultLabResultProcessingService implements LabResultProcessingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLabResultProcessingService.class);

    private final ImportServiceClient importServiceClient;
    private final ResultProcessor resultProcessor;

    public DefaultLabResultProcessingService(@Qualifier("restImportServiceClient") ImportServiceClient importServiceClient, ResultProcessor resultProcessor) {
        this.importServiceClient = importServiceClient;
        this.resultProcessor = resultProcessor;
    }

    @Override
    public void processLabResults() {
        LOGGER.info("Processing results...");

        Result result = importServiceClient.readResult();
        if (result != null) {
            LOGGER.info(String.format("Processing result with id '%s'", result.getId()));
            resultProcessor.processResult(new ResultProcessorContext(result.getId(), result.getValue()));
            importServiceClient.sendAcknowledgement(result.getId());
        } else {
            LOGGER.info("There is no available unprocessed results");
        }
    }
}
