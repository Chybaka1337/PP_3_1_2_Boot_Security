package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select distinct u from User u left join fetch u.roles where u.name = :username")
    Optional<User> findByName(@Param("username") String name);

    @Query("select distinct u from User u left join fetch u.roles")
    List<User> findAll();

    @Query(value =
            "select * from user join user_role " +
            "on user.id = user_role.user_id join role " +
            "on user_role.role_id = role.id " +
            "where role.role_name = :roleName",
            nativeQuery = true)
    Optional<User> findByRole(@Param("roleName") String roleName);
}
