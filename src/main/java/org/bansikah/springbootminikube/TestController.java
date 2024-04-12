package org.bansikah.springbootminikube;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/data")
    public String getData() {
        return "Hello World!, this is  a message from pod";
    }
}
