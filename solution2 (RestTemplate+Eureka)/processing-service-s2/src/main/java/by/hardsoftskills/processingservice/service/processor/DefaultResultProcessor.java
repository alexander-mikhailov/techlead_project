package by.hardsoftskills.processingservice.service.processor;

import by.hardsoftskills.processingservice.service.processor.context.ResultProcessorContext;
import org.springframework.stereotype.Component;

@Component
public class DefaultResultProcessor implements ResultProcessor {

    @Override
    public void processResult(ResultProcessorContext context) {
        Object unifiedResult = transformResult();
        save(unifiedResult);
    }

    protected Object transformResult() {
        return new Object(); //todo
    }

    protected void save(Object unifiedResult) {
        //save the unifiedResult in storage
    }
}
