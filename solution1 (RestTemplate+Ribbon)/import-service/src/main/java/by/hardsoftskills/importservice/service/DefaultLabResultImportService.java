package by.hardsoftskills.importservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultLabResultImportService implements LabResultImportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLabResultImportService.class);

    @Override
    public void importLabResults() {
        LOGGER.info("Importing results...");
        // 1. read from 3d party software
        // 2. save to DB
        // 3. send acknowledge
    }
}
