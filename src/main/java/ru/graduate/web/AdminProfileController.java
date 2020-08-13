package ru.graduate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.graduate.model.User;
import ru.graduate.repository.UserRepository;
import ru.graduate.service.UserService;

import java.util.List;

@RestController
@RequestMapping(UserProfileController.REST_URL)
public class AdminProfileController {

    static final String REST_URL = "/admin";

    private final Logger logger = LoggerFactory.getLogger(AdminProfileController.class);

    protected final UserService service;
    protected final UserRepository repository;

    @Autowired
    public AdminProfileController(UserService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        logger.info("update(user) {} ",user);
        service.update(user);
    }

    //проверено -
    //http://localhost:8080/admin/100001
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        logger.info("delete(id) {} ",id);
        service.delete(id);
    }

    //проверен -
    //http://localhost:8080/admin/100001
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public User getById(@PathVariable String id){
        int Id = Integer.parseInt(id);
        logger.info("getById(id) {} ",id);
        return repository.findById(Id).orElse(null);
    }

    //проверено -
    //http://localhost:8080/admin/
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll(){
        logger.info("getAll");
        return repository.findAll(Sort.by(Sort.Direction.DESC,"name","email"));
    }

//
//    public User getByEmail(String email){
//        Assert.notNull(email,"email is null, error");
//        return checkNotFound(userRepository.getByEmail(email),email);
//    }
//
//    public User getAllWithRoles(int id){
//        return checkNotFoundWithId(userRepository.getAllWithRoles(id),id);
//    }



}

