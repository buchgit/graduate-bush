package ru.graduate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduate.model.Restaurant;
import ru.graduate.repository.RestaurantRepository;
import ru.graduate.utils.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantService {
    public final RestaurantRepository repository;

    @Autowired
    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public Restaurant get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public Restaurant getByName(String name) {
        Assert.notNull(name, "restaurant name must not be null");
        return repository.getByName(name);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }

	@Cacheable("restaurants")
    public List<Restaurant> getActual(){
        return repository.findActualWithDish(LocalDate.now());
    }

}
