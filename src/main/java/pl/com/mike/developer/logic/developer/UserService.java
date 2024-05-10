package pl.com.mike.developer.logic.developer;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.developer.User;
import pl.com.mike.developer.domain.developer.UserData;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

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
        userRepository.save(user);
        return new UserData(user);
    }

}
