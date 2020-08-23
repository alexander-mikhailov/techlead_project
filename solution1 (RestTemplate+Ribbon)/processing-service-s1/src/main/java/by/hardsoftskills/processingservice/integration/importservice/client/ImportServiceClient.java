package by.hardsoftskills.processingservice.integration.importservice.client;

import by.hardsoftskills.processingservice.integration.importservice.client.api.exchange.Result;

public interface ImportServiceClient {
    Result readResult();

    void sendAcknowledgement(String resultId);
}
