package com.revature.security.props.users;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class Test {

    @GetMapping(path = "hello")
    public String test() {
        return "test endpoint works! :)";
    }

}
