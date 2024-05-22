package pl.com.mike.developer;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.domain.developer.UserData;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/auth/")
public class AuthenticationEndpoint {

    private final AuthenticationManager authenticationManager;
    private final String jwtSecret;
    private final long jwtExpirationInMillis;

    private final Logger log = org.slf4j.LoggerFactory.getLogger(AuthenticationEndpoint.class);

    public AuthenticationEndpoint(AuthenticationManager authenticationManager,
                                  @Value("${jwt.secret}") String jwtSecret,
                                  @Value("${jwt.expiration}") long jwtExpirationInMillis) {
        this.authenticationManager = authenticationManager;
        this.jwtSecret = jwtSecret;
        this.jwtExpirationInMillis = jwtExpirationInMillis;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserData user) {
        try {

            String decodedPassword = new String(Base64.getDecoder().decode(user.getPasswordHash()), StandardCharsets.UTF_8);
            log.info("Decoded password: {}", decodedPassword);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getLogin(), decodedPassword)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            long now = System.currentTimeMillis();
            String jwt = Jwts.builder()
                    .setSubject(user.getLogin())
                    .claim("roles", roles)
                    .setIssuedAt(new Date(now))
                    .setExpiration(new Date(now + jwtExpirationInMillis))
                    .signWith(SignatureAlgorithm.HS256, jwtSecret)
                    .compact();

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            return ResponseEntity.ok().body(response);

        } catch (AuthenticationException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Unauthorized");
            errorResponse.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        } catch (Exception e) {
            log.error("Error during decryption", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
