package ru.graduate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import ru.graduate.model.Dish;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish,Integer> {
    Dish getByName(String name);

    @Transactional
    @Modifying
    @Query("delete from Dish d where d.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Modifying
    Dish save(Dish dish);

    @Query("select d FROM Dish d where d.menu.id =:menuId order by d.price")
    List<Dish> getByMenu(@Param("menuId") int menuId);

    @Query("select d FROM Dish d where d.menu.id =(select m.id from Menu m where m.restaurant.id=:restaurantId) order by d.price")
    List<Dish> getByRestaurant(@Param("restaurantId") int restaurantId);
}
