package by.hardsoftskills.processingservice.config;

import by.hardsoftskills.processingservice.service.LabResultProcessingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@ConditionalOnProperty(
        value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
@EnableScheduling
public class SchedulingConfig {
    private final LabResultProcessingService labResultProcessingService;

    public SchedulingConfig(LabResultProcessingService labResultProcessingService) {
        this.labResultProcessingService = labResultProcessingService;
    }

    @Scheduled(fixedDelay = 1000)
    public void scheduleLabResultsProcessing() {
        labResultProcessingService.processLabResults();
    }
}
