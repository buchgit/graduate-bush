package ru.graduate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduate.model.Vote;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("delete from Vote v where v.id=:id and (:userId is null or v.user.id=:userId)")
    int delete(@Param("id") int id, @Param("userId") Integer userId);

    @Transactional
    @Modifying
    Vote save(Vote vote);

    @Query("select v from Vote v where v.date >=:startDate and v.date<=:endDate and (:restaurantID IS null or v.restaurant.id=:restaurantID) and (:userID IS null or v.user.id=:userID)")
    List<Vote> getAllFiltered(@Param("startDate") @NotNull LocalDateTime startDate,
                              @Param("endDate") @NotNull LocalDateTime endDate,
                              @Param("restaurantID") @Nullable Integer restaurantID,
                              @Param("userID") @Nullable Integer userID
    );
}
