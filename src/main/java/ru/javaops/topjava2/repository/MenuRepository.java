package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.MenuItem;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<MenuItem> {

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    List<MenuItem> findAll();

    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id=?1")
    List<MenuItem> findAllByRestaurant(int restaurant_id);

    List<MenuItem> getMenuItemByRestaurantIdAndDate(int id, LocalDate date);
}
