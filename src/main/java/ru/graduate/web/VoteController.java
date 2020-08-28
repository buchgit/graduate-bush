package ru.graduate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduate.LoggedUser;
import ru.graduate.model.Vote;
import ru.graduate.repository.VoteRepository;
import ru.graduate.service.VoteService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.graduate.utils.ValidationUtil.getStringResponseEntity;

@RestController
@RequestMapping(VoteController.VOTE_URL)
public class VoteController {
    static final String VOTE_URL = "/rest/votes";

    private Logger logger = LoggerFactory.getLogger(VoteController.class);

    private final VoteService service;

    @Autowired
    public VoteController(VoteRepository repository, VoteService service) {
        this.service = service;
    }

    /*
     *** General section ***
     */

    @GetMapping(value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAllFiltered(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  startDate,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  endDate,
                                     @RequestParam(required = false) @Nullable Integer restaurantId,
                                     @RequestParam(required = false) @Nullable Integer userId
    ){
        logger.info("getAllFiltered(...) {} {} {} ",startDate,endDate,restaurantId,userId);
        return service.getAllFiltered(startDate,endDate,restaurantId,userId);
    }

    @PostMapping
    public ResponseEntity<Vote> create (@RequestParam int restaurantId,
                                        @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  date,
                                        @AuthenticationPrincipal LoggedUser loggedUser){
        Vote created = service.create(restaurantId, date, loggedUser.getId());
        URI responseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VOTE_URL+"/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        logger.info("create(restaurantId,date,userId) {} {} {} ",restaurantId,date,loggedUser.getId());
        return ResponseEntity.created(responseUri).body(created);
    }

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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @AuthenticationPrincipal LoggedUser loggedUser){
        logger.info("delete(id) {} ",loggedUser.getId());
        service.delete(id, loggedUser.getId());
    }

    /*
     *** Admin section ***
     */

    @DeleteMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        logger.info("delete(id) {} ",id);
        service.delete(id,null);
    }
}
