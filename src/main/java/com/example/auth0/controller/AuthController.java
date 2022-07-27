package com.example.auth0.controller;


import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.auth0.configuration.AuthConfig;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import com.auth0.AuthenticationController;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;


@Controller
    public class AuthController {

        @Autowired
        private AuthConfig config;

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private AuthenticationController authenticationController;

    @GetMapping(value = "/login")
    protected void login(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        String redirectUri = config.getContextPath(request)+"/callback";
        String authorizeUrl = authenticationController.buildAuthorizeUrl(request, response, redirectUri)
                .withScope("openid email profile")
                .build();
        response.sendRedirect(authorizeUrl);
    }



    @GetMapping(value="/callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws IdentityVerificationException, IOException, ServletException {
        Tokens tokens = authenticationController.handle(request, response);
        DecodedJWT jwt = JWT.decode(tokens.getIdToken());
        TestingAuthenticationToken authToken2 = new TestingAuthenticationToken(jwt.getSubject(),
                jwt.getToken());
        authToken2.setAuthenticated(true);

       SecurityContextHolder.getContext().setAuthentication(authToken2);

        JSONObject requestBody = new JSONObject();
        requestBody.put("token", tokens.getIdToken());
        System.out.println(requestBody);
        String url = "http://localhost:8082/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(),headers);
        restTemplate.postForObject(url, entity, String.class);

    }


    }

