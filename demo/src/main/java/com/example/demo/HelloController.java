package com.example.demo;
import com.example.demo.pack1.ActivityTypes;
import com.example.demo.pack1.Branch;
import com.example.demo.pack2.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/user")
    private String user() {


        User u = new User();
        u.setName("Sahan");

        return u.getName();
    }

    @GetMapping("/bank")
    private String bank() {

        Branch b = new Branch();
        return b.getAmount();
    }

    @GetMapping("/bank/get/{value}")
    public String getBankValue(@PathVariable double value) {

        Branch b = new Branch();
        b.activity(ActivityTypes.WITHDRAWAL, value);
        return b.getAmount();
    }

    @GetMapping("/bank/set/{value}")
    public String setBankValue(@PathVariable double value) {

        Branch b = new Branch();
        b.activity(ActivityTypes.SAVING, value);
        return b.getAmount();
    }

}

