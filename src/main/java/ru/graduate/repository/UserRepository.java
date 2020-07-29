package ru.graduate.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduate.model.User;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User,Integer> {

    @Transactional
    @Modifying
    @Query("delete from User u where u.id=:id")
    int delete(@Param("id") int id);

    User getByEmail(String email);//проверить как цепляет

    @EntityGraph(attributePaths = "roles")
    @Query("select u from User u where u.id=?1")
    User getAllWithRoles(int id);

}
