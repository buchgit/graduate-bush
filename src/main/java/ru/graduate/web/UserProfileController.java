package ru.graduate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.graduate.LoggedUser;
import ru.graduate.model.User;
import ru.graduate.repository.UserRepository;
import ru.graduate.service.UserService;

import javax.validation.Valid;

import static ru.graduate.utils.ValidationUtil.getStringResponseEntity;

@RestController
@RequestMapping(UserProfileController.REST_URL)
public class UserProfileController {

    static final String REST_URL = "/user";

    private final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    protected final UserService service;
    protected final UserRepository repository;

    @Autowired
    public UserProfileController(UserService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    //проверено -
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return getStringResponseEntity(result, logger);
        } else {
            User createdUser = service.create(user);
            logger.info("create(user) {} ", createdUser.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    //проверено -
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@Valid @RequestBody User user, @AuthenticationPrincipal LoggedUser loggedUser,BindingResult result) {
        if (result.hasErrors()){
            return getStringResponseEntity(result, logger);
        }else{
            User updatedUser = service.update(user, loggedUser.getId());
            logger.info("update(user) {} ",updatedUser.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        }
     }

    //проверено -
    //http://localhost:8080/user/100001
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal LoggedUser loggedUser) {
        logger.info("delete(id) {} ",loggedUser.getUsername());
        service.delete(loggedUser.getId());
    }

    //проверен -
    //http://localhost:8080/user/
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal LoggedUser user){
        logger.info("get() {} ",user.getUsername());
        return repository.findById(user.getId()).orElse(null);
    }

}
