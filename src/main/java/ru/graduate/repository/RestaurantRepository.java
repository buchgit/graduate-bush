package ru.graduate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduate.model.Restaurant;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Restaurant getByName(String name);

    @Transactional
    @Modifying
    @Query("delete from Restaurant r where r.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Modifying
    Restaurant save(Restaurant restaurant);
}
