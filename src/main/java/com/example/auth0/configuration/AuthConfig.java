package com.example.auth0.configuration;

import com.auth0.AuthenticationController;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Configuration
    @EnableWebSecurity
    public class AuthConfig extends WebSecurityConfigurerAdapter {
        @Value(value = "${com.auth0.domain}")
        private String domain;

        @Value(value = "${com.auth0.clientId}")
        private String clientId;

        @Value(value = "${com.auth0.clientSecret}")
        private String clientSecret;



        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http
                    .authorizeRequests()
                    .antMatchers("/callback", "/login", "/","/home").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .and()
                    .logout().permitAll();

        }

    @Bean
    public AuthenticationController authenticationController() throws UnsupportedEncodingException {
        JwkProvider jwkProvider = new JwkProviderBuilder(domain).build();
        return AuthenticationController.newBuilder(domain, clientId, clientSecret)
                .withJwkProvider(jwkProvider)
                .build();
    }


    public String getClientId() {
        return clientId;
    }



    public String getContextPath(HttpServletRequest request) {
        String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return path;
    }
    }
