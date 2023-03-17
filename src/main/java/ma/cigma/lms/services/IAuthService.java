package ma.cigma.lms.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAuthService extends UserDetailsService {
    public UserDetails loadUserByEmail(String email);
}
