package ru.graduate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.graduate.LoggedUser;
import ru.graduate.model.User;
import ru.graduate.repository.UserRepository;
import ru.graduate.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(UserProfileController.REST_URL)
public class UserProfileController {

    static final String REST_URL = "/users";

    private final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    protected final UserService service;
    protected final UserRepository repository;

    @Autowired
    public UserProfileController(UserService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    //проверено -
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @AuthenticationPrincipal LoggedUser loggedUser) {
        logger.info("update(user) {} ",user.getName());
        service.update(user, loggedUser.getId());
    }

    //проверено -
    //http://localhost:8080/users/100001
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal LoggedUser loggedUser) {
        logger.info("delete(id) {} ",loggedUser.getUsername());
        service.delete(loggedUser.getId());
    }

    //проверен -
    //http://localhost:8080/users/100001
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal LoggedUser user){
        logger.info("get() {} ",user.getUsername());
        return repository.findById(user.getId()).orElse(null);
    }
}
