package ru.graduate.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduate.model.Vote;
import ru.graduate.repository.VoteRepository;
import ru.graduate.service.VoteService;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.VOTE_URL)
public class VoteController {
    static final String VOTE_URL = "/votes";

    private final VoteRepository repository;
    private final VoteService service;

    @Autowired
    public VoteController(VoteRepository repository, VoteService service) {
        this.repository = repository;
        this.service = service;
    }
    //проверен -
    @PostMapping
    public ResponseEntity<Vote> create (@RequestParam int restaurantId, @RequestParam LocalDate date, @RequestParam int userId){
        Vote created = service.create(restaurantId, date, userId);
        URI responseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VOTE_URL+"/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(responseUri).body(created);
    }
    //проверен -
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable int id){
        return service.get(id);
    }
    //проверен -
    @GetMapping(value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getByUser(@RequestParam int userId){
        return service.getByUser(userId);
    }
    //проверен -
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        service.delete(id);
    }
    //проверен -
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update (Vote vote){
        service.update(vote);
    }
    //проверен -
    @GetMapping(value = "/restaurant",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getByRestaurant(@RequestParam int restaurantId){
        return repository.getByRestaurant(restaurantId);
    }
    //проверен -
    @GetMapping(value = "/berween",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getBetween(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  startDate,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  endDate) {
        return service.getBetween(startDate, endDate);
    }




}
