package by.hardsoftskills.importservice.api.exchange;

public class Result {
    private final String id;
    private final String value;
    private boolean processed;

    public Result(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
