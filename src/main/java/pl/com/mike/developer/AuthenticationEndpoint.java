package pl.com.mike.developer;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.domain.developer.UserData;

import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("api/auth/")
public class AuthenticationEndpoint {

    private final AuthenticationManager authenticationManager;
    private final String jwtSecret;
    private final long jwtExpirationInMillis;

    private final Logger log = org.slf4j.LoggerFactory.getLogger(AuthenticationEndpoint.class);

    public AuthenticationEndpoint(AuthenticationManager authenticationManager, @Value("${jwt.secret}") String jwtSecret, @Value("${jwt.expiration}") long jwtExpirationInMillis) {
        this.authenticationManager = authenticationManager;
        this.jwtSecret = jwtSecret;
        this.jwtExpirationInMillis = jwtExpirationInMillis;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserData user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPasswordHash())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            long now = System.currentTimeMillis();
            String jwt = Jwts.builder()
                    .setSubject(user.getLogin())
                    .setIssuedAt(new Date(now))
                    .setExpiration(new Date(now + jwtExpirationInMillis)) // Sets expiration to 24 hours
                    .signWith(SignatureAlgorithm.HS256, jwtSecret) // Replace later the key
                    .compact();

            return ResponseEntity.ok().body(Collections.singletonMap("token", jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}