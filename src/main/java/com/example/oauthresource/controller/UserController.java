package com.example.oauthresource.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("profile")
    public @ResponseBody String getOauth2Principal(OAuth2Authentication auth) {
        return "Access granted for " + auth.getPrincipal();
    }
}