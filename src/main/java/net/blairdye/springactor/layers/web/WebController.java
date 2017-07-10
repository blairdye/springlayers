package net.blairdye.springactor.layers.web;

import net.blairdye.springactor.layers.service.AskDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

/**
 * Created by blaird on 6/07/17.
 */
@RestController("WebController_1_0")
@RequestMapping("/api/web")
public class WebController {

    @Autowired
    private AskDataSourceService askDataSourceService;

    public class Result{
        public String message;

        public Result(String message) {
            this.message = message;
        }
    }

    @GetMapping(path = "/uppercase",
            produces = "application/vnd.com.orchestral.hpd-v1_0+json")
    public Object search(@RequestParam(value="value",required=true)String value){
        String capitals = askDataSourceService.queryDataSource(value);
        return new Result(capitals);
    }

}
