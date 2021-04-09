package com.example.demo.controller;

import com.example.demo.entity.AuthRequest;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private AuthenticationManager authenticationManager;

  @GetMapping("/")
  public String welcome() {
    return "Welcome to Sandeep's World!";
  }

  @PostMapping("/auth")
  public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
              authRequest.getPassword()));
    } catch (Exception ex) {
      throw new Exception("Invalid Credentials...!");
    }
    return jwtUtil.generateToken(authRequest.getUsername());
  }
}
