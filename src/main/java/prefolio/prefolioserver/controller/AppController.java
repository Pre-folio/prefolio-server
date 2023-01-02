package prefolio.prefolioserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/api")
public class AppController {

    @GetMapping("/hello")
    public String Hello(){
        return "hello";
    }

}
