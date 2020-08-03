package ru.graduate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduate.model.Dish;
import ru.graduate.model.Menu;
import ru.graduate.repository.MenuRepository;
import ru.graduate.utils.TimeUtils;
import ru.graduate.utils.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MenuService {
    public final MenuRepository repository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.repository = menuRepository;
    }

//    public Menu create(Menu menu, int restaurantId){
//        Assert.notNull(menu,"Dish must not be null");
//        menu.setRestaurant(repository.getOne(restaurantId));
//        return repository.save(menu);
//    }

    public Menu get(int id){
        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null),id);
    }

    public void delete (int id){
        ValidationUtil.checkNotFoundWithId(repository.delete(id)!=0,id);
    }

    public void update (Menu menu){
        Assert.notNull(menu,"Dish must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(menu),menu.getId());
    }

    public List<Menu> getByRestaurant(int restaurantId){
        return repository.getByRestaurant(restaurantId);
    }

    public List<Menu> getBetween(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startOfDay = TimeUtils.toBeginOfDay(startDate);
        LocalDateTime endOfDay = TimeUtils.toEndOfDay(endDate);
        return repository.getBetween(startOfDay, endOfDay);
    }



}
