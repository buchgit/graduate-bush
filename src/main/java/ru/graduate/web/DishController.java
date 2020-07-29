package ru.graduate.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduate.model.Dish;
import ru.graduate.repository.DishRepository;
import ru.graduate.service.DishService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(DishController.DISH_URL)
public class DishController {

    static final String DISH_URL = "/dishes";

    private final DishService service;

    private final DishRepository repository;

    @Autowired
    public DishController(DishService service, DishRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    //проверен-
    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        return service.get(id);
    }
    //проверен+
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll(){
        return repository.findAll(Sort.by(Sort.Direction.DESC,"name"));
    }

    //проверен-
    @PostMapping
    public ResponseEntity<Dish> create (@RequestBody Dish dish){
        Dish created = service.create(dish);
        URI responseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DISH_URL+"/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(responseUri).body(created);
    }

    //проверен-
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    //проверен-
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update (@PathVariable int id,@RequestBody Dish dish){
        service.update(id,dish);
    }
    //проверен -
    @GetMapping(value = "/name",produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish getByName(@RequestParam String name){
        return service.getByName(name);
    }
    //проверен +
    @GetMapping(value = "/menu",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getByMenu(@RequestParam String id){
        Integer Id = Integer.parseInt(id);
        return repository.getByMenu(Id);
    }
    //проверен -
    @GetMapping(value = "/restaurant",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getByRestaurant(@RequestParam String id){
        Integer Id = Integer.parseInt(id);
        return repository.getByRestaurant(Id);
    }
}
