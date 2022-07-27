package com.example.auth0.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class HomeController {


@GetMapping(value = "/register")
public String register() {

    return "In Register ";
}

    @GetMapping(value="/home")
    public String HelloMethod(@RequestParam String email) {
        System.out.println("in hello method ");
        return "Welcome "+email;
    }

}
