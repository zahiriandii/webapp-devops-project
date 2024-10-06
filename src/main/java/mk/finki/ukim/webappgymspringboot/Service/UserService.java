package mk.finki.ukim.webappgymspringboot.Service;

import mk.finki.ukim.webappgymspringboot.Model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();
    Optional<User> saveUser(String username, String name, String surname, String password);

}

