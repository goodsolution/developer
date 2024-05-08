package pl.com.mike.developer.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.domain.developer.UserData;
import pl.com.mike.developer.logic.developer.UserService;

@RestController
@RequestMapping("api/")
public class UserEndpoint {
    private final UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user/register")
    public ResponseEntity<?> registerUser(@RequestBody UserData userData) {
        UserData user = userService.createUser(userData);
        return ResponseEntity.ok(user);
    }

}
