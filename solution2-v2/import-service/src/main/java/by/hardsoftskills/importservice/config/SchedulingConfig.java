package by.hardsoftskills.importservice.config;

import by.hardsoftskills.importservice.exchange.producer.LabResultImportService;
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
    private final LabResultImportService labResultImportService;

    public SchedulingConfig(LabResultImportService labResultImportService) {
        this.labResultImportService = labResultImportService;
    }

    @Scheduled(fixedDelay = 5000)
    public void scheduleLabResultsImport() {
        labResultImportService.importLabResults();
    }
}
