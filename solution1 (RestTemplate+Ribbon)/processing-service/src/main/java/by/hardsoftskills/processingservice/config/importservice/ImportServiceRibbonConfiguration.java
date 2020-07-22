package by.hardsoftskills.processingservice.config.importservice;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportServiceRibbonConfiguration {

    @Bean
    public IPing ribbonPing() {
        return new PingUrl(false, "/actuator/health");
    }

    @Bean
    public IRule ribbonRule() {
        return new AvailabilityFilteringRule();
    }
}