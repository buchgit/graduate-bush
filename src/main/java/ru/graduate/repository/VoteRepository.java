package ru.graduate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduate.model.Dish;
import ru.graduate.model.Vote;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote,Integer> {

    @Transactional
    @Modifying
    @Query("delete from Vote v where v.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Modifying
    Vote save(Vote vote);

//    @Query("select v FROM Vote v where v.user.id =:userId order by v.date")
//    List<Vote> getByUser(@Param("userId") int userId);
//
//    @Query("select v from Vote v where v.restaurant.id =:restaurantId")
//    List<Vote> getByRestaurant(@Param("restaurantId") int restaurantId);
//
//    @Query("select v from Vote v where v.date >=:startDate and v.date<=:endDate")
//    List<Vote> getBetween(@Param("startDate") @NotNull LocalDateTime startDate,
//                          @Param("endDate") @NotNull LocalDateTime endDate);

    @Query("select v from Vote v where v.date >=:startDate and v.date<=:endDate and (v.restaurant IS null or v.restaurant=:restaurantID) and (v.user IS null or v.user=:userID)")
    List<Vote> getAllFiltered(@Param("startDate") @NotNull LocalDateTime startDate,
                          @Param("endDate") @NotNull LocalDateTime endDate,
                          @Param("restaurantID") @Nullable int restaurantID,
                          @Param("userID")  @Nullable int userID
    );
}
