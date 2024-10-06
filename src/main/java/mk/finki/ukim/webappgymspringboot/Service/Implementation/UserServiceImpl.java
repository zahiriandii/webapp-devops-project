package mk.finki.ukim.webappgymspringboot.Service.Implementation;


import mk.finki.ukim.webappgymspringboot.Model.Exceptions.UserNotFoundException;
import mk.finki.ukim.webappgymspringboot.Model.User;
import mk.finki.ukim.webappgymspringboot.Repository.UserRepository;
import mk.finki.ukim.webappgymspringboot.Service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> saveUser(String username, String name, String surname, String password) {
        User user = new User(username,name,surname,password);
        return Optional.of(userRepository.save(user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) this.userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }
}
