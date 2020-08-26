package ru.graduate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduate.LoggedUser;
import ru.graduate.model.Vote;
import ru.graduate.repository.VoteRepository;
import ru.graduate.service.VoteService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.graduate.utils.ValidationUtil.getStringResponseEntity;

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

    //User***************************************************************************************************
    //user:проверен -
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
                                        @AuthenticationPrincipal LoggedUser loggedUser){
        Vote created = service.create(restaurantId, date, loggedUser.getId());
        URI responseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VOTE_URL+"/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        logger.info("create(restaurantId,date,userId) {} {} {} ",restaurantId,date,loggedUser.getId());
        return ResponseEntity.created(responseUri).body(created);
    }

    //user:проверен -
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
    public ResponseEntity<String> update (@Valid @RequestBody Vote vote, BindingResult result , @AuthenticationPrincipal LoggedUser loggedUser){
        if (result.hasErrors()){
            return getStringResponseEntity(result, logger);
        }else{
            service.update(vote,loggedUser.getId());
            logger.info("update (vote) {} ",vote);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //проверен -
    //http://localhost:8080/votes/admin/100012
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal LoggedUser loggedUser){
        logger.info("delete(id) {} ",loggedUser.getId());
        service.delete(loggedUser.getId());
    }

    //user:проверен -
    @GetMapping(value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAllFiltered(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  startDate,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  endDate,
                                     @RequestParam(required = false) int restaurantId,
                                     @RequestParam(required = false) int userId
                                     ){
        logger.info("getAllFiltered(...) {} {} {} ",startDate,endDate,restaurantId,userId);
        return service.getAllFiltered(startDate,endDate,restaurantId,userId);
    }

//    //user:проверен -
//    //http://localhost:8080/votes/user?userId=100000
//    @GetMapping(value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Vote> getAllOwn(@AuthenticationPrincipal LoggedUser loggedUser){
//        logger.info("getByUser(userId) {} ",loggedUser.getId());
//        return service.getByUser(loggedUser.getId());
//    }

//    //user://проверен -
//    //http://localhost:8080/votes/restaurant?restaurantId=100003
//    @GetMapping(value = "/restaurant",produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Vote> getByRestaurant(@RequestParam int restaurantId){
//        logger.info("getByRestaurant(restaurantId) {} ",restaurantId);
//        return repository.getByRestaurant(restaurantId);
//    }
//
//    //user://проверен -
//    //http://localhost:8080/votes/between?startDate=2020-07-02&endDate=2020-07-02
//    @GetMapping(value = "/between",produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Vote> getBetween(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  startDate,
//                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  endDate) {
//        logger.info("getBetween(startDate, endDate) {} {} ",startDate, endDate);
//        return service.getBetween(startDate, endDate);
//    }
//    //проверен -
//    //http://localhost:8080/votes/admin/100012
//    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
//    public Vote get(@PathVariable int id){
//        logger.info("get(id) {} ",id);
//        return service.get(id);
//    }






    //Admin only*************************************************************************************************




    //проверен -
    //http://localhost:8080/votes/admin/100012
    @DeleteMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        logger.info("delete(id) {} ",id);
        service.delete(id);
    }

//    //проверен -
//    @GetMapping(value = "/admin",produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Vote> getAll(){
//        logger.info("getAll");
//        return repository.findAll();
//    }




}
