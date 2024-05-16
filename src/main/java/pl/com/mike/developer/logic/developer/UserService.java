package pl.com.mike.developer.logic.developer;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.developer.User;
import pl.com.mike.developer.domain.developer.UserAuthority;
import pl.com.mike.developer.domain.developer.UserData;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private static final String ROLE = "ROLE_DEVELOPER";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserData getUserDataByLogin(String login) {
        Optional<User> user = userRepository.getUserByLogin(login);
        if (user.isPresent()) {
            return new UserData(user.get());
        } else {
            throw new NoSuchElementException("User with login " + login + " not found");
        }
    }

    public UserData createUser(UserData userData) {
        User user = userData.toUser();
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        Set<UserAuthority> authorities = new HashSet<>();

        UserAuthority authority = new UserAuthority();
        authority.setUser(user);
        authority.setAuthority(ROLE);
        authorities.add(authority);
        user.setUserAuthorities(authorities);

        userRepository.save(user);
        return new UserData(user);
    }

}
