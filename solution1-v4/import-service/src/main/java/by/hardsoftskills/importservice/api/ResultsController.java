package by.hardsoftskills.importservice.api;

import by.hardsoftskills.importservice.api.exchange.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultsController {
    private final List<Result> results;

    public ResultsController() {
        int size = 10;
        List<Result> results = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            results.add(new Result(String.valueOf(i), "Result" + i));
        }
        this.results = Collections.unmodifiableList(results);
    }

    @GetMapping("/unprocessed")
    public Result getUnprocessedResult() {
        return results.stream()
                .filter(result -> !result.isProcessed())
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/{id}/acknowledge")
    public Result acknowledgeResult(@PathVariable String id) {
        Result result = results.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        result.setProcessed(true);
        return result;
    }
}
