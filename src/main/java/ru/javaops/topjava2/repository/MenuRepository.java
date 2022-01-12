package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.MenuItem;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<MenuItem> {

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @NonNull
    List<MenuItem> findAll();

    List<MenuItem> getMenuItemByRestaurantIdAndDate(int id, LocalDate date);

    List<MenuItem> getMenuItemByDate(LocalDate date);
}
