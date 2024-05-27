package pl.com.mike.developer;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
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
import pl.com.mike.developer.config.developer.EncryptionConfig;
import pl.com.mike.developer.config.developer.JwtConfig;
import pl.com.mike.developer.domain.developer.UserData;
import pl.com.mike.developer.exceptions.EncryptionException;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
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
    private final JwtConfig jwtConfig;
    private final EncryptionConfig encryptionConfig;
    private final Logger log = org.slf4j.LoggerFactory.getLogger(AuthenticationEndpoint.class);

    public AuthenticationEndpoint(AuthenticationManager authenticationManager,
                                  JwtConfig jwtConfig,
                                  EncryptionConfig encryptionConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.encryptionConfig = encryptionConfig;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserData user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getLogin(), decrypt(user.getPasswordHash())
                    ));
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
                    .setExpiration(new Date(now + jwtConfig.getJwtExpirationInMillis()))
                    .signWith(SignatureAlgorithm.HS256, jwtConfig.getJwtSecret())
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
        } catch (EncryptionException e) {
            log.error("Error during decryption", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String decrypt(String encryptedData) {
        try {
            byte[] encryptedBytesWithIv = Base64.getDecoder().decode(encryptedData);
            byte[] iv = new byte[encryptionConfig.getGcmIVLength()];
            byte[] encryptedBytes = new byte[encryptedBytesWithIv.length - encryptionConfig.getGcmIVLength()];
            System.arraycopy(encryptedBytesWithIv, 0, iv, 0, iv.length);
            System.arraycopy(encryptedBytesWithIv, iv.length, encryptedBytes, 0, encryptedBytes.length);
            Cipher cipher = Cipher.getInstance(encryptionConfig.getAesGcmNoPadding());
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(encryptionConfig.getGcmTagLength() * 8, iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(encryptionConfig.getKey()), encryptionConfig.getAes());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new EncryptionException("Error while decrypting", e);
        }
    }

}
