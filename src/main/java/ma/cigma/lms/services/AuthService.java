package ma.cigma.lms.services;

import lombok.extern.slf4j.Slf4j;
import ma.cigma.lms.models.User;
import ma.cigma.lms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class AuthService implements IAuthService{

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByEmail(String email) {
        log.info("[ AUTH SERVICE ] ~ [ LOAD USER BY USERNAME ]");
        User user = userRepository.findByEmailIgnoreCase(email);

         if(user == null)
         throw new UsernameNotFoundException("Could not findUser with email = " + email);
         else if (!user.isEnabled())
         throw new DisabledException("User is not Enabled");

        return new org.springframework.security.core.userdetails.User(
                email,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
