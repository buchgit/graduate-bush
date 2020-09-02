package ru.graduate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.graduate.model.User;
import ru.graduate.repository.UserRepository;
import ru.graduate.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.graduate.utils.ValidationUtil.getStringResponseEntity;

@RestController
@RequestMapping(AdminProfileController.REST_URL)
public class AdminProfileController {

    static final String REST_URL = "/rest/admin";

    private final Logger logger = LoggerFactory.getLogger(AdminProfileController.class);

    protected final UserService service;
    protected final UserRepository repository;

    @Autowired
    public AdminProfileController(UserService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

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

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return getStringResponseEntity(result, logger);
        } else {
            User updatedUser = service.update(user, user.getId());
            logger.info("update(user) {} ", updatedUser.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        logger.info("delete(id) {} ", id);
        service.delete(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable String id) {
        int Id = Integer.parseInt(id);
        logger.info("get(id) {} ", id);
        return repository.findById(Id).orElse(null);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        logger.info("getAll");
        return repository.findAll(Sort.by(Sort.Direction.DESC, "name", "email"));
    }

    @GetMapping("/email")
    public User getByEmail(@RequestParam String email) {
        return service.getByEmail(email);
    }

    @PostMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        service.enable(id, enabled);
    }
}

