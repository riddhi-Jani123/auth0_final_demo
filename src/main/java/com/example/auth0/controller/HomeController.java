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


//    @GetMapping(value = "/")
//    @ResponseBody
//    public String home(final Authentication authentication) {
//        TestingAuthenticationToken token = (TestingAuthenticationToken) authentication;
//        DecodedJWT jwt = JWT.decode(token.getCredentials().toString());
//        String email = jwt.getClaims().get("email").asString();
//        return "Welcome, " + email + "!";
//    }

//    @GetMapping(value = "/home")
//    @ResponseBody
//    public String home(HttpServletRequest request, HttpServletResponse response, final Authentication authentication) throws IOException {
//        System.out.println("Authentication obj is :" + authentication);
//        if (authentication != null && authentication instanceof TestingAuthenticationToken) {
//            TestingAuthenticationToken token = (TestingAuthenticationToken) authentication;
//            System.out.println("Token is " + token);
//            DecodedJWT jwt = JWT.decode(token.getCredentials().toString());
//            String email = jwt.getClaims().get("email").asString();
//
//            return "Welcome, " + email + "!";
//        } else {
//            response.sendRedirect("http://localhost:8080/login");
//            return null;
//        }
//
//    }
@GetMapping(value = "/register")
protected String register() {

    return "In Register ";
}

    @GetMapping(value="/home")
    public String HelloMethod(@RequestParam String email) {
        System.out.println("in hello method ");
        return "Welcome "+email;
    }

}
