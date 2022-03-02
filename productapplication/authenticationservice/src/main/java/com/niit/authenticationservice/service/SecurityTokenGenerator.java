package com.niit.authenticationservice.service;

import com.niit.authenticationservice.domain.User;

import java.util.Map;

public interface SecurityTokenGenerator {
    Map<String,String> generateToken(User user);
}
