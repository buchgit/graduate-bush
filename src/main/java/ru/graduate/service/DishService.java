package ru.graduate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduate.model.Dish;
import ru.graduate.repository.DishRepository;
import ru.graduate.repository.MenuRepository;
import ru.graduate.utils.TimeUtils;
import ru.graduate.utils.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

@Service
public class DishService {
    public final DishRepository repository;
    public final MenuRepository menuRepository;

    @Autowired
    public DishService(DishRepository repository, MenuRepository menuRepository) {
        this.repository = repository;
        this.menuRepository = menuRepository;
    }

    public Dish create(Dish dish, int menuId) {
        Assert.notNull(dish, "Dish must not be null");
        dish.setMenu(menuRepository.getOne(menuId));
        return repository.save(dish);
    }

    public Dish get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public Dish getByName(String name) {
        Assert.notNull(name, "Dish name must not be null");
        return repository.getByName(name);
    }

    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public void update(Dish dish) {
        Assert.notNull(dish, "Dish must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(dish), dish.getId());
    }

    public List<Dish> getByMenu(int menuId) {
        return repository.getByMenu(menuId);
    }

    public List<Dish> getBetweenByRestaurant(LocalDate startDate, LocalDate endDate, Integer restaurantId) {
        LocalDate startDay = TimeUtils.toMinDate(startDate);
        LocalDate endDay = TimeUtils.toMaxDate(endDate);
        return repository.getBetweenByRestaurant(startDay, endDay, restaurantId);
    }
}
