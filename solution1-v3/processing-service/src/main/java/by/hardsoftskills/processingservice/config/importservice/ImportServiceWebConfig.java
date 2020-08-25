package by.hardsoftskills.processingservice.config.importservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;

@Configuration
public class ImportServiceWebConfig extends WebMvcConfigurationSupport {

    @Bean
    public RestTemplate importServiceRestTemplate(RestTemplateBuilder builder) {
        return builder
                .additionalInterceptors(new LoggingRequestInterceptor())
                .build();
    }

    private static class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
        private static final Logger LOGGER = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            LOGGER.info(String.format("Sending request to %s", request.getURI().toString()));
            return execution.execute(request, body);
        }
    }
}
