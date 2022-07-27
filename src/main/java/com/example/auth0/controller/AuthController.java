package com.example.auth0.controller;


import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.auth0.configuration.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.auth0.AuthenticationController;

import java.io.IOException;
import java.util.HashMap;


@RestController
    public class AuthController {
        @Autowired
        private AuthConfig config;

        @Autowired
        private AuthenticationController authenticationController;

//    @GetMapping(value = "/login")
//    protected void login(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
//        String redirectUri = "http://localhost:8082/callback";
//        String authorizeUrl = authenticationController.buildAuthorizeUrl(request, response, redirectUri)
//                .withScope("openid email profile")
//                .build();
//        response.sendRedirect(authorizeUrl);
//    }
//
//    @GetMapping(value="/callback")
//    public void callback(HttpServletRequest request, HttpServletResponse response) throws IdentityVerificationException, IOException {
//
//        Tokens tokens = authenticationController.handle(request, response);
//        System.out.println("hello");
//        DecodedJWT jwt = JWT.decode(tokens.getIdToken());
//        TestingAuthenticationToken authToken2 = new TestingAuthenticationToken(jwt.getSubject(),
//                jwt.getToken());
//        authToken2.setAuthenticated(true);
//        System.out.println("Token is :"+ jwt);
//        SecurityContextHolder.getContext().setAuthentication(authToken2);
//        System.out.println("path "+ config.getContextPath(request));
//        response.sendRedirect("https://www.google.com/");
//
//        //response.sendRedirect(config.getContextPath(request) + "/home");
//    }

    public String getManagementApiToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestBody = new JSONObject();
        requestBody.put("client_id", config.getManagementApiClientId());
        requestBody.put("client_secret", config.getManagementApiClientSecret());
        requestBody.put("audience", "https://auth0-services.us.auth0.com/api/v2/");
        requestBody.put("grant_type", "client_credentials");

        HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, String> result = restTemplate.postForObject("https://auth0-services.us.auth0.com/oauth/token", request, HashMap.class);

        return result.get("access_token");
    }

    }

