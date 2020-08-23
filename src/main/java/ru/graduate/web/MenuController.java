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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduate.model.Dish;
import ru.graduate.model.Menu;
import ru.graduate.repository.MenuRepository;
import ru.graduate.service.MenuService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.graduate.utils.ValidationUtil.getStringResponseEntity;

@RestController
@RequestMapping(MenuController.MENU_URL)
public class MenuController {
    static final String MENU_URL = "/menus";

    private final Logger logger = LoggerFactory.getLogger(MenuController.class);

    private final MenuService service;

    private final MenuRepository repository;

    @Autowired
    public MenuController(MenuService service, MenuRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    /*
     *** General section ***
     */

//    //проверен -
//    //http://localhost:8080/
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Menu> getAll(){
//        logger.info("getAll");
//        return repository.findAll(Sort.by(Sort.Direction.DESC,"date"));
//    }

    //проверен -
    //http://localhost:8080/menus?startDate=2020-07-02&endDate=2020-07-02&restaurantId=100003
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAllFiltered(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                     @RequestParam(required = false) int restaurantId) {
        logger.info("getBetween(startDate,endDate) {} {} ", startDate, endDate);
        return service.getAllFiltered(startDate, endDate, restaurantId);
    }

    /*
     *** Admin section ***
     */

    //проверен -
    //http://localhost:8080/menus/admin/100008
    @GetMapping("/admin/{id}")
    public Menu get(@PathVariable int id) {
        logger.info("get(id) {} ",id);
        return service.get(id);
    }

    //проверен-
    //http://localhost:8080/menus/admin?restaurantId=100002&date=2020-07-06
    @PostMapping("/admin")
//    public ResponseEntity<Menu> create (@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
//                                        @RequestParam int restaurantId){
    public ResponseEntity<Menu> create (@RequestParam(required = true) @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                        @RequestParam int restaurantId){
        Menu created = service.create(date,restaurantId);
        URI responseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MENU_URL+"/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        logger.info("create (date,restaurantId {} {} ",date,restaurantId);
        return ResponseEntity.created(responseUri).body(created);
    }

    //проверен -
    /*
    {
        "id": 100010,
        "date": "2020-08-11T00:00:00",
        "restaurant": {
            "id": 100003
        }
    }
     */
    @PutMapping("/admin")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update (@Valid @RequestBody Menu menu, BindingResult result){
        if (result.hasErrors()){
            return getStringResponseEntity(result, logger);
        }else{
            service.update(menu);
            logger.info("update (menu) {} ",menu);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //проверен -
    //http://localhost:8080/menus/100007
    @DeleteMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        logger.info("delete(id {} ",id);
        service.delete(id);
    }



//    //проверен +
//    //http://localhost:8080/menus/restaurant/?id=100003
//    @GetMapping(value = "/restaurant",produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Menu> getByRestaurant(@RequestParam int id){
//        logger.info("getByRestaurant(id) {} ",id);
//        return repository.getByRestaurant(id);
//    }
//
//    //проверен +
//    //http://localhost:8080/menus/between?startDate=2020-07-02&endDate=2020-07-02
//    @GetMapping(value = "/between",produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Menu> getBetween(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  endDate){
//        logger.info("getBetween(startDate,endDate) {} {} ",startDate,endDate);
//        return service.getBetween(startDate,endDate);
//    }
}
