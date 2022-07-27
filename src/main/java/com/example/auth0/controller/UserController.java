package com.example.auth0.controller;

import com.example.auth0.configuration.AuthConfig;
import com.example.auth0.service.ApiService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    AuthController authController;

    @Autowired
    AuthConfig config;
    @Autowired
    private ApiService apiService;

    @GetMapping(value="/users")
    @ResponseBody
    public ResponseEntity<String> users(HttpServletRequest request, HttpServletResponse response) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + authController.getManagementApiToken());

        System.out.println("token  "  +authController.getManagementApiToken());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate
                .exchange("https://dev-zr9pcrck.us.auth0.com/api/v2/users", HttpMethod.GET, entity, String.class);
        return result;
    }

    @GetMapping(value = "/createUser")
    @ResponseBody
    public ResponseEntity<String> createUser(HttpServletResponse response) {
        JSONObject request = new JSONObject();
        request.put("email", "jaydeepr.inexture@gmail.com");
        request.put("given_name", "Norman");
        request.put("family_name", "Lewis");
        request.put("connection", "Username-Password-Authentication");
        request.put("password", "R@j$#181");

        ResponseEntity<String> result = apiService.postCall(config.getUsersUrl(), request.toString());
        return result;
    }
}
