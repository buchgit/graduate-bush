package ru.graduate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduate.model.User;
import ru.graduate.repository.UserRepository;

import java.util.List;

import static ru.graduate.utils.ValidationUtil.checkNotFoundWithId;
import static ru.graduate.utils.ValidationUtil.checkNotFound;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user){
        Assert.notNull(user,"user is null, error");
        return userRepository.save(user);
    }

    public User update(User user){
        Assert.notNull(user,"user is null, error");
        return checkNotFoundWithId(userRepository.save(user),user.getId());
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
}
