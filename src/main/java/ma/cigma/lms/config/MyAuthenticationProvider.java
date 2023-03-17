package ma.cigma.lms.config;

import lombok.extern.slf4j.Slf4j;
import ma.cigma.lms.models.User;
import ma.cigma.lms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Lazy
    @Autowired
    UserRepository userRepository;
    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("[ PK Authentication Provider ]");
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user != null && user.getEmail().equals(email) && passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(email, password, Collections.emptyList());
        } else {
            throw new BadCredentialsException("User authentication failed!!!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
