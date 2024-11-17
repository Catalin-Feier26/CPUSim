package org.example.cpusim;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/api/hello")
    public String sayHello() {
        return "MIPS 32 BITS PIPELINE ARCHITECTURE IS SUPERIOR!";
    }
}
