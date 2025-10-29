package com.HelloUser.HelloUser;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")

public class adminController {

    private final Map<String,String> tokens = new ConcurrentHashMap<>();

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
    var u = body.get("username"); var p = body.get("password");
    if ("admin".equals(u) && "admin".equals(p)) {
      var token = UUID.randomUUID().toString();
      tokens.put(token, u);
      return ResponseEntity.ok(Map.of("token", token, "user", u));
    }
    return ResponseEntity.status(401).body(Map.of("error","Fel inloggning"));
  }

  @GetMapping("/verify/{token}")
  public ResponseEntity<?> verify(@PathVariable String token) {
    return tokens.containsKey(token)
      ? ResponseEntity.ok(Map.of("valid", true, "user", tokens.get(token)))
      : ResponseEntity.status(401).body(Map.of("valid", false));
  }

  
  @Bean public Map<String,String> tokenStore() { return tokens; }

@PostMapping("/logout")
public ResponseEntity<?> logout(@RequestBody Map<String, String> body) {
    String token = body.get("token");
    if (token != null) tokens.remove(token);
    return ResponseEntity.ok(Map.of("message", "Logged out"));
}

}

