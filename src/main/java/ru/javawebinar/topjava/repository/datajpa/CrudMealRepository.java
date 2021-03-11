package ru.javawebinar.topjava.repository.datajpa;

import org.hibernate.type.LocalDateTimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Query("SELECT u FROM User u WHERE u.id=:id")
    User getByUser(@Param("id") int id);

    @Transactional
    @Query("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    Meal getOne(
            @Param("id") int id,
            @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int deleteByIdAndUserId(
            @Param("id") int id,
            @Param("userId") int userId);

    @Transactional
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId")
    List<Meal>  getAll(
            @Param("userId") int userId);


    @Transactional
    @DateTimeFormat(pattern = "yyyy-MM-dd HH.mm")
    @Query("SELECT m FROM Meal m WHERE m.user.id=?3 AND m.dateTime >= ?1 AND m.dateTime < ?2 ORDER BY m.dateTime DESC")
    List<Meal> findByBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);
}
