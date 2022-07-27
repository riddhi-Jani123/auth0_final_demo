package com.example.auth0.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.auth0.dto.TokenDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RestController
public class HomeController {
//    @Autowired
//    private RestTemplate restTemplate;
    @PostMapping(value = "/")
    public ResponseEntity<Void> home(HttpServletRequest request, HttpServletResponse response, @RequestBody TokenDTO token) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
        System.out.println("token "+token.getToken());
        DecodedJWT jwt = JWT.decode(token.getToken());
        TestingAuthenticationToken authToken2 = new TestingAuthenticationToken(jwt.getSubject(),
                jwt.getToken());
        authToken2.setAuthenticated(true);
       SecurityContextHolder.getContext().setAuthentication(authToken2);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication obj is :" + authentication);
        if (authentication != null && authentication instanceof TestingAuthenticationToken) {
            TestingAuthenticationToken tokenOg = (TestingAuthenticationToken) authentication;
            System.out.println("Token is " + tokenOg);
            DecodedJWT jwt1 = JWT.decode(tokenOg.getCredentials().toString());
            String email = jwt1.getClaims().get("email").asString();
            System.out.println("email"+email);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/home?email="+email+"")).build();
        } else {
            System.out.println("register");
            response.sendRedirect("http://localhost:8080/register");
            return ResponseEntity.status(HttpStatus.OK).location(URI.create("http://localhost:8080/register")).build();
        }
    }




}