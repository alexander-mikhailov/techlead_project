package by.hardsoftskills.processingservice.service.processor.context;

public class ResultProcessorContext {
    private String resultId;
    private String rawResult;

    public ResultProcessorContext(String resultId, String rawResult) {
        this.resultId = resultId;
        this.rawResult = rawResult;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getRawResult() {
        return rawResult;
    }

    public void setRawResult(String rawResult) {
        this.rawResult = rawResult;
    }
}
