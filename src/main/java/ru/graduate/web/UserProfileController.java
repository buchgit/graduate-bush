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
import ru.graduate.model.Role;
import ru.graduate.model.User;
import ru.graduate.repository.UserRepository;
import ru.graduate.service.UserService;

import javax.validation.Valid;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static ru.graduate.utils.ValidationUtil.getStringResponseEntity;

@RestController
@RequestMapping(UserProfileController.REST_URL)
public class UserProfileController {

    static final String REST_URL = "/rest/user";

    private final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    protected final UserService service;
    protected final UserRepository repository;

    @Autowired
    public UserProfileController(UserService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return getStringResponseEntity(result, logger);
        } else {
            Set<Role> roles = new HashSet<Role>();
            roles.add(Role.USER);
            user.setRoles(roles);
            User createdUser = service.create(user);
            logger.info("create(user) {} ", createdUser.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@Valid @RequestBody User user, @AuthenticationPrincipal LoggedUser loggedUser, BindingResult result) {
        if (result.hasErrors()) {
            return getStringResponseEntity(result, logger);
        } else {
            User updatedUser = service.update(user, loggedUser.getId());
            logger.info("update(user) {} ", updatedUser.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal LoggedUser loggedUser) {
        logger.info("delete(id) {} ", loggedUser.getUsername());
        service.delete(loggedUser.getId());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal LoggedUser user) {
        logger.info("get() {} ", user.getUsername());
        return repository.findById(user.getId()).orElse(null);
    }
}
