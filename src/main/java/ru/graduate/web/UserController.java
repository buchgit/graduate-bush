package ru.graduate.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.graduate.model.User;
import ru.graduate.repository.UserRepository;
import ru.graduate.service.UserService;

import java.util.List;

@RestController
@RequestMapping(UserController.REST_URL)
public class UserController {

    static final String REST_URL = "/users";

    protected final UserService service;
    protected final UserRepository repository;

    @Autowired
    public UserController(UserService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        service.update(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public User getById(@PathVariable String id){
        int Id = Integer.parseInt(id);
        return repository.findById(Id).orElse(null);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll(){
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
