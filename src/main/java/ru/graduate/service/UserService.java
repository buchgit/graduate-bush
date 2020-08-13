package ru.graduate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduate.LoggedUser;
import ru.graduate.model.User;
import ru.graduate.repository.UserRepository;

import static ru.graduate.utils.ValidationUtil.checkNotFoundWithId;
import static ru.graduate.utils.ValidationUtil.checkNotFound;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user){
        Assert.notNull(user,"user is null, error");
        return userRepository.save(user);
    }

    public User update(User user, int id){
        Assert.notNull(user,"user is null, error");
        return checkNotFoundWithId(userRepository.save(user),id);
    }

    public void delete(int id){
        checkNotFoundWithId(userRepository.delete(id),id);
    }

    public User getById(int id){
        return userRepository.findById(id).orElse(null);
    }
// из контроллера напрямую из репозитория
//    public List<User> getAll(){
//        return userRepository.findAll(Sort.by(Sort.Direction.DESC,"name","email"));
//    }

    public User getByEmail(String email){
        Assert.notNull(email,"email is null, error");
        return checkNotFound(userRepository.getByEmail(email),email);
    }

    public User getAllWithRoles(int id){
        return checkNotFoundWithId(userRepository.getAllWithRoles(id),id);
    }

    @Override
    public LoggedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new LoggedUser(user);
    }
}
