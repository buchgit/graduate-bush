package ru.graduate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduate.model.Restaurant;
import ru.graduate.repository.RestaurantRepository;
import ru.graduate.service.RestaurantService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(RestaurantController.RESTAURANTS_URL)
public class RestaurantController {

    static final String RESTAURANTS_URL = "/restaurants";

    private final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    private final RestaurantService service;

    private final RestaurantRepository repository;

    @Autowired
    protected RestaurantController(RestaurantService service, RestaurantRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    //проверен+
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        logger.info("get(id) {} ",id);
        return service.get(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll(){
        logger.info("getAll");
        return repository.findAll(Sort.by(Sort.Direction.DESC,"name"));
    }

    //проверен+
    @PostMapping
    public ResponseEntity<Restaurant> create (@RequestBody Restaurant restaurant){
        Restaurant created = service.create(restaurant);
        URI responseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANTS_URL+"/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        logger.info("create (restaurant) {} ",restaurant);
        return ResponseEntity.created(responseUri).body(created);
    }

    //проверен+
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        logger.info("delete(id) {} ",id);
        service.delete(id);
    }

    //проверен +
    //PUT http://localhost:8080/restaurants
    //body
    /*
    {
        "id": 100004,
        "name": "Restaurant 333"
    }
     */
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update (@RequestBody Restaurant restaurant){
        logger.info("update (restaurant) {} ",restaurant);
        service.update(restaurant);
    }
    //проверен +
    @GetMapping(value = "/name",produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getByName(@RequestParam String name){
        logger.info("getByName(name) {} ",name);
        return service.getByName(name);
    }

}
