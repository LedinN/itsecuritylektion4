package dev.nick.itsecuritylektion4.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/maquina")
    public String hello() {
        return "Hello from Maquina";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello from Admin";
    }
}
