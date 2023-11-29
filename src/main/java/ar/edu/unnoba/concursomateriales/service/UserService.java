package ar.edu.unnoba.concursomateriales.service;
import ar.edu.unnoba.concursomateriales.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User create(User user);
    User findByEmail(String email);

}
