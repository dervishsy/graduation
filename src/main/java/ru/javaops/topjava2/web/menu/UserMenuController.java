package ru.javaops.topjava2.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.model.MenuItem;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.util.DateTimeUtil;

import java.util.List;

@RestController
@RequestMapping(value = UserMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "menu")
public class UserMenuController {
    public static final String REST_URL = "/api/menu";

    @Autowired
    MenuRepository repository;

    @GetMapping("/{restaurant_id}")
    @Cacheable
    public List<MenuItem> getAllMenuByRestaurant(@PathVariable int restaurant_id) {
        log.info("getByRestaurant {}", restaurant_id);

        return repository.getMenuItemByRestaurantIdAndDate(restaurant_id, DateTimeUtil.getCurrentDate());
    }
}
