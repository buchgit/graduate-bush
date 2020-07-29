package ru.graduate.web;

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
        return service.get(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll(){
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
        return ResponseEntity.created(responseUri).body(created);
    }

    //проверен+
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    //проверен+
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update (@PathVariable int id,@RequestBody Restaurant restaurant){
        service.update(id,restaurant);
    }
    //проверен +
    @GetMapping(value = "/name",produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getByName(@RequestParam String name){
        return service.getByName(name);
    }

}
