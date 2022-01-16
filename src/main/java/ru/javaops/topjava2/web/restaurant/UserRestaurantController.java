package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.util.DateTimeUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class UserRestaurantController {
    public static final String REST_URL = "/api/restaurants";

    @Autowired
    RestaurantRepository repository;

    @Autowired
    MenuRepository menuRepository;

    @GetMapping()
    @Cacheable
    public List<Restaurant> getAllWithMenu() {
        log.info("with-menu");
        List<Restaurant> restaurants = repository.findAll();
        restaurants.stream()
                .filter(Objects::nonNull)
                .forEach(r -> r.setMenu(menuRepository.getMenuItemByRestaurantIdAndDate(r.getId(), DateTimeUtil.getCurrentDate())));
        return restaurants;
    }

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Restaurant> getWithMenuById(@PathVariable int id) {
        log.info("{}/with-current-menu", id);
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new IllegalRequestDataException("No Restaurant with id:" + id));
        restaurant.setMenu(menuRepository.getMenuItemByRestaurantIdAndDate(id, DateTimeUtil.getCurrentDate()));
        return ResponseEntity.of(Optional.of(restaurant));
    }
}
