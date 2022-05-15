package com.example.crmapi.user;

import com.example.crmapi.security.AuthenticationFacade;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.sql.Date;
import java.util.HashMap;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterForm userRegisterForm) {
        try {
            User user = new User(userRegisterForm);
            return ResponseEntity.ok().body(userService.saveUser(user));
        } catch (Exception e) {
            HashMap<String, String> expectation = new HashMap<>();
            expectation.put("Error message", e.getMessage());
            return ResponseEntity.badRequest().body(expectation);
        }
    }
    @PostMapping("/get-by-email/{email}")
    public User getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }
    @GetMapping("/getUserName")
    public String getUserName() {
        return authenticationFacade.getUserEmail();
    }

    @GetMapping("/getUserId")
    public long getUserId() {
        return authenticationFacade.getUserId();
    }

}

