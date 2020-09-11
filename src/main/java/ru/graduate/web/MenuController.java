package ru.graduate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduate.model.Menu;
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
    static final String MENU_URL = "/rest";

    private final Logger logger = LoggerFactory.getLogger(MenuController.class);

    private final MenuService service;

    @Autowired
    public MenuController(MenuService service) {
        this.service = service;
    }

    /*
     *** General section ***
     */

    @GetMapping(value = "/menus", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAllFiltered(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                     @RequestParam(required = false) @Nullable Integer restaurantId) {
        logger.info("getBetween(startDate,endDate) {} {} {}", startDate, endDate, restaurantId);
        return service.getAllFiltered(startDate, endDate, restaurantId);
    }

    /*
     *** Admin section ***
     */

    @GetMapping("/admin/menus/{id}")
    public Menu get(@PathVariable int id) {
        logger.info("get(id) {} ", id);
        return service.get(id);
    }

    @PostMapping("/admin/menus")
    public ResponseEntity<Menu> create(@RequestParam(required = true) @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                       @RequestParam int restaurantId) {
        Menu created = service.create(date, restaurantId);
        URI responseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MENU_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        logger.info("create (date,restaurantId {} {} ", date, restaurantId);
        return ResponseEntity.created(responseUri).body(created);
    }

    @PutMapping("/admin/menus")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@Valid @RequestBody Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            return getStringResponseEntity(result, logger);
        } else {
            service.update(menu);
            logger.info("update (menu) {} ", menu);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/admin/menus/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        logger.info("delete(id {} ", id);
        service.delete(id);
    }
}
