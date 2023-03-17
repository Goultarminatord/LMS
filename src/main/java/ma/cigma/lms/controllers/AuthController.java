package ma.cigma.lms.controllers;

import lombok.extern.slf4j.Slf4j;
import ma.cigma.lms.models.User;
import ma.cigma.lms.services.UserService;
import ma.cigma.lms.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    UserService userService;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<Object> loginHandler(@RequestBody User body){
        log.info("[ AUTH CONTROLLER ] ~ [ LOGIN ]");
        String password = encoder.encode("P@ssw0rd");
        System.out.println("P@ssw0rd = "+password);
        UsernamePasswordAuthenticationToken authInputToken;
        try {
            authInputToken = new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());
            authManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(body.getEmail());
            Map<String, Object> response = new HashMap<>();
            response.put("user",userService.getOne(body.getEmail()));
            response.put("jwt-token", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (DisabledException ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
//            throw ex;
        }catch (AuthenticationException authExc){
            log.error(authExc.getMessage());
            return new ResponseEntity<>("Invalid Login Credentials", HttpStatus.UNAUTHORIZED);
//            throw new RuntimeException("Invalid Login Credentials");
        }catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//            throw ex;
        }
    }
}
