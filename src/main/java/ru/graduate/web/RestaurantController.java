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
import ru.graduate.model.Restaurant;
import ru.graduate.repository.RestaurantRepository;
import ru.graduate.service.RestaurantService;

import javax.validation.Valid;
import java.util.List;

import static ru.graduate.utils.ValidationUtil.getStringResponseEntity;

@RestController
@RequestMapping(RestaurantController.RESTAURANTS_URL)
public class RestaurantController {

    static final String RESTAURANTS_URL = "/rest/restaurants";

    private final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    private final RestaurantService service;

    private final RestaurantRepository repository;

    @Autowired
    protected RestaurantController(RestaurantService service, RestaurantRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    /*
     *** General section ***
     */

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        logger.info("getAll");
        return repository.findAll(Sort.by(Sort.Direction.DESC, "name"));
    }

    @GetMapping(value = "/name", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getByName(@RequestParam String name) {
        logger.info("getByName(name) {} ", name);
        return service.getByName(name);
    }

    @GetMapping(value = "/actual")
    public List<Restaurant>getActual(){
        logger.info("getActual()");
        return service.getActual();
    }

    /*
     *** Admin section ***
     */

    @GetMapping("/admin/{id}")
    public Restaurant get(@PathVariable int id) {
        logger.info("get(id) {} ", id);
        return service.get(id);
    }

    @PostMapping("/admin")
    public ResponseEntity<String> create(@Valid @RequestBody Restaurant restaurant, BindingResult result) {
        if (result.hasErrors()) {
            return getStringResponseEntity(result, logger);
        } else {
            Restaurant created = service.create(restaurant);
            logger.info("create(restaurant) {} ", created);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PutMapping("/admin")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@Valid @RequestBody Restaurant restaurant, BindingResult result) {
        if (result.hasErrors()) {
            return getStringResponseEntity(result, logger);
        } else {
            service.update(restaurant);
            logger.info("update (restaurant) {} ", restaurant);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        logger.info("delete(id) {} ", id);
        service.delete(id);
    }
}
