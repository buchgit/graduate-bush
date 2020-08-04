package ru.graduate.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduate.model.Menu;
import ru.graduate.repository.MenuRepository;
import ru.graduate.service.MenuService;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(MenuController.MENU_URL)
public class MenuController {
    static final String MENU_URL = "/menus";

    private final MenuService service;

    private final MenuRepository repository;

    @Autowired
    public MenuController(MenuService service, MenuRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    //проверен+
    //http://localhost:8080/menus/100008
    @GetMapping("/{id}")
    public Menu get(@PathVariable int id) {
        return service.get(id);
    }

    //проверен+
    //http://localhost:8080/
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAll(){
        return repository.findAll(Sort.by(Sort.Direction.DESC,"date"));
    }

    //проверен+
    //http://localhost:8080/menus?restaurantId=100002&date=2020-07-06
    @PostMapping
    public ResponseEntity<Menu> create (@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                        @RequestParam int restaurantId){
        Menu created = service.create(date,restaurantId);
        URI responseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MENU_URL+"/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(responseUri).body(created);
    }

    //проверен+
    //http://localhost:8080/menus/100007
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    //проверен+
    /*
    {
        "id": 100010,
        "date": "2020-08-11T00:00:00",
        "restaurant": {
            "id": 100003
        }
    }
     */
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update (@RequestBody Menu menu){
        service.update(menu);
    }

    //проверен +
    //http://localhost:8080/menus/restaurant/?id=100003
    @GetMapping(value = "/restaurant",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getByRestaurant(@RequestParam int id){
        return repository.getByRestaurant(id);
    }

    //проверен +
    //http://localhost:8080/menus/between?startDate=2020-07-02&endDate=2020-07-02
    @GetMapping(value = "/between",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getBetween(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  endDate){
        return service.getBetween(startDate,endDate);
    }
}
