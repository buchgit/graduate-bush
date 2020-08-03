package ru.graduate.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduate.model.Dish;
import ru.graduate.repository.DishRepository;
import ru.graduate.service.DishService;

import java.net.URI;
import java.time.LocalDate;
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

    //проверен+
    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        return service.get(id);
    }

    //проверен+
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll(){
        return repository.findAll(Sort.by(Sort.Direction.DESC,"name"));
    }

    //проверен+ но dish.menu в body прилетает как null
    @PostMapping
    public ResponseEntity<Dish> create (@RequestBody Dish dish,
                                        @RequestParam int menuId){
        Dish created = service.create(dish,menuId);
        URI responseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DISH_URL+"/{id}")
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

    //проверен
    /*
    {
        "id": 100023,
        "name": "dish 666",
        "menu": {
            "id":100008
            },
        "price": 88.77
    }
     */
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update (@RequestBody Dish dish){
        service.update(dish);
    }

    //проверен +
    //http://localhost:8080/dishes/name?name=dish 9
    @GetMapping(value = "/name",produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish getByName(@RequestParam String name){
        return service.getByName(name);
    }

    //проверен +
    @GetMapping(value = "/menu",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getByMenu(@RequestParam int id){
        return repository.getByMenu(id);
    }

    //проверен +
    /*
    http://localhost:8080/dishes//restaurant?id=100003
     */
    @GetMapping(value = "/restaurant",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getByRestaurant(@RequestParam int id){
        return repository.getByRestaurant(id);
    }

    //проверен +
    //http://localhost:8080/dishes/between?startDate=2020-07-02&endDate=2020-07-02
    @GetMapping(value = "/between",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getBetween(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  startDate,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  endDate){
        return service.getBetween(startDate,endDate);
    }
}
