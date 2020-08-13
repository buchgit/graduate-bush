package ru.graduate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(VoteController.VOTE_URL)
public class VoteController {
    static final String VOTE_URL = "/votes";

    private Logger logger = LoggerFactory.getLogger(VoteController.class);

    private final VoteRepository repository;
    private final VoteService service;

    @Autowired
    public VoteController(VoteRepository repository, VoteService service) {
        this.repository = repository;
        this.service = service;
    }

    //проверен +
    //http://localhost:8080/votes
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAll(){
        logger.info("getAll");
        return repository.findAll();
    }


    //проверен +
    //http://localhost:8080/votes?restaurantId=100002&date=2020-08-04&userId=100001
    /* response
    {
        "id": 100029,
        "user": null,
        "restaurant": null,
        "date": "2020-08-04T00:00:00"
    }
     */
    @PostMapping
    public ResponseEntity<Vote> create (@RequestParam int restaurantId,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  date,
                                        @RequestParam int userId){
        Vote created = service.create(restaurantId, date, userId);
        URI responseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VOTE_URL+"/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        logger.info("create(restaurantId,date,userId) {} {} {} ",restaurantId,date,userId);
        return ResponseEntity.created(responseUri).body(created);
    }

    //admin
    //проверен +
    //http://localhost:8080/votes/100012
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable int id){
        logger.info("get(id) {} ",id);
        return service.get(id);
    }

    //проверен +
    //http://localhost:8080/votes/user?userId=100000
    @GetMapping(value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getByUser(@RequestParam int userId){
        logger.info("getByUser(userId) {} ",userId);
        return service.getByUser(userId);
    }

    //admin
    //проверен +
    //http://localhost:8080/votes/100012
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        logger.info("delete(id) {} ",id);
        service.delete(id);
    }

    //проверен +
    //http://localhost:8080/votes
    /* body
    {
        "id": 100013,
        "user": {
            "id": 100001
        },
        "restaurant": {
            "id": 100002
        },VOTES
        "date": "2020-08-30T00:00:00"
    }
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update (@RequestBody Vote vote){
        logger.info("update (vote) {} ",vote);
        service.update(vote);
    }

    //проверен +
    //http://localhost:8080/votes/restaurant?restaurantId=100003
    @GetMapping(value = "/restaurant",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getByRestaurant(@RequestParam int restaurantId){
        logger.info("getByRestaurant(restaurantId) {} ",restaurantId);
        return repository.getByRestaurant(restaurantId);
    }

    //проверен +
    //http://localhost:8080/votes/between?startDate=2020-07-02&endDate=2020-07-02
    @GetMapping(value = "/between",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getBetween(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  startDate,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  endDate) {
        logger.info("getBetween(startDate, endDate) {} {} ",startDate, endDate);
        return service.getBetween(startDate, endDate);
    }
}
