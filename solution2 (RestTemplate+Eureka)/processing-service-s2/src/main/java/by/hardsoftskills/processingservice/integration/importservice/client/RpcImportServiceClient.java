package by.hardsoftskills.processingservice.integration.importservice.client;

import by.hardsoftskills.processingservice.integration.importservice.client.api.exchange.Result;
import org.springframework.stereotype.Component;

@Component
public class RpcImportServiceClient implements ImportServiceClient {
    @Override
    public Result readResult() {
        return null;
    }

    @Override
    public void sendAcknowledgement(String resultId) {
        //todo
    }
}
