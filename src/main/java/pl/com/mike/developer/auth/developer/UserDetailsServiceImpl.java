package pl.com.mike.developer.auth.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.mike.developer.domain.developer.User;
import pl.com.mike.developer.logic.developer.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepositoryAuth) {
        this.userRepository = userRepositoryAuth;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) {
        User user = userRepository.getUserByLogin(login).orElseThrow(() -> new RuntimeException("User not found: " + login));
        logger.info("Loaded user: {} with password: {}", user.getLogin(), user.getPasswordHash());
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_DEVELOPER"));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPasswordHash(), grantedAuthorities);
    }

}
