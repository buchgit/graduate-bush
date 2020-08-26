package ru.graduate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduate.model.Restaurant;
import ru.graduate.model.User;
import ru.graduate.model.Vote;
import ru.graduate.repository.RestaurantRepository;
import ru.graduate.repository.UserRepository;
import ru.graduate.repository.VoteRepository;
import ru.graduate.utils.TimeUtils;
import ru.graduate.utils.ValidationUtil;
import ru.graduate.utils.exceptions.TimeIsOverException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static ru.graduate.utils.ValidationUtil.timeIsOver;

@Service
public class VoteService {
    public final VoteRepository repository;
    public final RestaurantRepository restaurantRepository;
    public final UserRepository userRepository;

    @Autowired
    public VoteService(VoteRepository repository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Vote create(int restaurantId, LocalDate date, int userId){
        Assert.notNull(date,"Voting date must not be null");
        Vote vote = new Vote(userRepository.getOne(userId),restaurantRepository.getOne(restaurantId),TimeUtils.toBeginOfDay(date));
        return repository.save(vote);
    }
    //user: own votes only, to 11 am
    public void update (Vote vote, int userId){
        Assert.notNull(vote,"Vote must not be null");
        if (timeIsOver(vote.getDate().toLocalTime())){
            throw new TimeIsOverException("time needs to be before 11 am");
        }
        vote.setUser(userRepository.getOne(userId));
        ValidationUtil.checkNotFoundWithId(repository.save(vote),vote.getId());
    }

    public void delete (int id){
        ValidationUtil.checkNotFoundWithId(repository.delete(id)!=0,id);
    }

    public List<Vote> getAllFiltered(LocalDate startDate, LocalDate endDate, Integer restaurantId, Integer userId){
        LocalDateTime startOfDay = TimeUtils.toBeginOfDay(startDate);
        LocalDateTime endOfDay = TimeUtils.toEndOfDay(endDate);
        return repository.getAllFiltered(startOfDay, endOfDay, restaurantId, userId);
    }


//    //admin only
//    public Vote get(int id){
//        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null),id);
//    }
//
//    public List<Vote> getByUser(int userId){
//        return repository.getByUser(userId);
//    }
//
//
//
//
//    public List<Vote> getBetween(LocalDate startDate, LocalDate endDate) {
//        LocalDateTime startOfDay = TimeUtils.toBeginOfDay(startDate);
//        LocalDateTime endOfDay = TimeUtils.toEndOfDay(endDate);
//        return repository.getBetween(startOfDay, endOfDay);
//    }
}
