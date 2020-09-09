package ru.graduate.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduate.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @PersistenceContext
    EntityManager em = null;

    Restaurant getByName(String name);

    @Transactional
    @Modifying
    @Query("delete from Restaurant r where r.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Modifying
    Restaurant save(Restaurant restaurant);

    @EntityGraph(value = "graph-restaurant-menus", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menus m WHERE m.date=:date")
    List<Restaurant> findActualWithDish(@Param("date") LocalDate date);

}
