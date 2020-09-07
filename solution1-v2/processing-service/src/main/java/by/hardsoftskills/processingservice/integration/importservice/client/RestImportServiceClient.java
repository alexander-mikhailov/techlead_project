package by.hardsoftskills.processingservice.integration.importservice.client;

import by.hardsoftskills.processingservice.integration.importservice.client.api.exchange.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class RestImportServiceClient implements ImportServiceClient {
    private static final String READ_UNPROCESSED_RESULT_ENDPOINT = "/api/results/unprocessed";
    private static final String SEND_ACKNOWLEDGEMENT_RESULT_ENDPOINT = "/api/results/{id}/acknowledge";

    private final RestTemplate restTemplate;
    private final LoadBalancerClient loadBalancer;
    private final String serviceId;

    private final ThreadLocal<String> importServiceBaseUrl = new ThreadLocal<>();

    public RestImportServiceClient(RestTemplate restTemplate, LoadBalancerClient loadBalancer, @Value("${app.importServiceId: import-service}") String serviceId) {
        this.restTemplate = restTemplate;
        this.loadBalancer = loadBalancer;
        this.serviceId = serviceId;
    }

    @Override
    public Result readResult() {
        ServiceInstance serviceInstance = loadBalancer.choose(serviceId);

        importServiceBaseUrl.set(serviceInstance.getUri().toString());

        return restTemplate.getForObject(getFullUrl(READ_UNPROCESSED_RESULT_ENDPOINT), Result.class);
    }

    @Override
    public void sendAcknowledgement(String resultId) {
        Map<String, String> vars = new HashMap<>();
        vars.put("id", resultId);
        restTemplate.postForObject(getFullUrl(SEND_ACKNOWLEDGEMENT_RESULT_ENDPOINT), null, Result.class, vars);
    }

    String getFullUrl(String endpoint) {
        return importServiceBaseUrl.get() + endpoint;
    }
}
