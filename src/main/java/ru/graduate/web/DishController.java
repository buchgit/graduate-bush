package ru.graduate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.graduate.model.Dish;
import ru.graduate.repository.DishRepository;
import ru.graduate.service.DishService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static ru.graduate.utils.ValidationUtil.getStringResponseEntity;

@RestController
@RequestMapping(DishController.DISH_URL)
public class DishController {

    static final String DISH_URL = "/dishes";

    private final Logger logger = LoggerFactory.getLogger(DishController.class);

    private final DishService service;

    private final DishRepository repository;

    @Autowired
    public DishController(DishService service, DishRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    /*
     *** General section ***
     */

//    //проверен+
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Dish> getAll(){
//        logger.info("getAll");
//        return repository.findAll(Sort.by(Sort.Direction.DESC,"name"));
//    }

    //проверен +
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getBetweenByRestaurant(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                 @RequestParam(required = false) Integer restaurantId) {
        logger.info("getBetween(startDate,endDate) {} {} {} ", startDate, endDate, restaurantId);
        return service.getBetweenByRestaurant(startDate, endDate, restaurantId);
    }

    //проверен +
    @GetMapping(value = "/name",produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish getByName(@RequestParam String name){
        logger.info("getByName(name) {} ",name);
        return service.getByName(name);
    }

    //проверен +
    @GetMapping(value = "/menu",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getByMenu(@RequestParam int id){
        logger.info("getByMenu(id) {} ",id);
        return repository.getByMenu(id);
    }

//    //проверен -
//    /*
//    http://localhost:8080/dishes//restaurant?id=100003
//     */
//    @GetMapping(value = "/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Dish> getByRestaurant(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
//                                      @RequestParam int id) {
//        logger.info("getByRestaurant(startDate, endDate, id) {} {} {} ", startDate, endDate, id);
//        return repository.getByRestaurant(startDate, endDate, id);
//    }



    /*
     *** Admin section ***
     */

    //проверен -
    @GetMapping("/admin/{id}")
    public Dish get(@PathVariable int id) {
        logger.info("get(id) {} ",id);
        return service.get(id);
    }

    //проверен +
    @PostMapping("/admin")
    public ResponseEntity<String> create(@Valid @RequestBody Dish dish,
                                       @RequestParam int menuId,
                                       BindingResult result
    ) {
        if (result.hasErrors()){
            return getStringResponseEntity(result, logger);
        }else{
            Dish created = service.create(dish,menuId);
            logger.info("create(dish) {} ",created);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    //проверен +
    /*
    {
        "id": 100018,
        "name": "dish 666",
        "menu": {
            "id":100008
            },
        "price": 88.77
    }
     */
    @PutMapping("/admin")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update (@Valid @RequestBody Dish dish, BindingResult result){
        if (result.hasErrors()){
            return getStringResponseEntity(result, logger);
        }else{
            service.update(dish);
            logger.info("update (dish) {} ",dish);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //проверен +
    @DeleteMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        logger.info("delete(id) {} ",id);
        service.delete(id);
    }
}
