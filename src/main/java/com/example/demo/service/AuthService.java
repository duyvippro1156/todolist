package com.example.demo.service;

import com.example.demo.dto.SignInDto;
import com.example.demo.dto.SignUpDto;

public interface AuthService {
    String login(SignInDto signinDto);
    void register(SignUpDto signUpDto);
}