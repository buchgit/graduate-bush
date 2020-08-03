package ru.graduate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduate.model.Dish;
import ru.graduate.model.Menu;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository <Menu,Integer>{
    @Transactional
    @Modifying
    @Query("delete from Menu m where m.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Modifying
    Menu save(Menu menu);

    @Query("select m FROM Menu m where m.id =:id order by m.date")
    List<Menu> get(@Param("id") int id);

    @Query("select m from Menu m where m.restaurant.id =:restaurantId")
    List<Menu> getByRestaurant(@Param("restaurantId") int restaurantId);

    @Query("select m from Menu m where m.date >=:startDate and m.date<=:endDate")
    List<Menu> getBetween(@Param("startDate") @NotNull LocalDateTime startDate,
                          @Param("endDate") @NotNull LocalDateTime endDate);
}
