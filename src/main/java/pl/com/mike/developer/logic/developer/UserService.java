package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.developer.User;
import pl.com.mike.developer.domain.developer.UserData;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserData getUserDataByLogin(String login) {
        Optional<User> user = userRepository.getUserByLogin(login);
        if (user.isPresent()) {
            return new UserData(user.get());
        } else {
            throw new NoSuchElementException("User with login " + login + " not found");
        }
    }

}
