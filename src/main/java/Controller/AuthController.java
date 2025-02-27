package Controller;

import Util.JwtService;
import Service.UserService;
import TableDao.User;
import Util.TokenBlacklistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthController(UserService userService, JwtService jwtService, TokenBlacklistService tokenBlacklistService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        User user = userService.register(request.get("email"),request.get("username"), request.get("password"));
        return ResponseEntity.ok(Map.of("id", user.getId(), "email", user.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        Optional<User> user = userService.findByEmail(request.get("email"));
        if (user.isPresent()) {
            String token = jwtService.generateToken(user.get().getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        tokenBlacklistService.blacklistToken(token);
        return ResponseEntity.ok(Map.of("message", "User logged out successfully"));
    }
}
