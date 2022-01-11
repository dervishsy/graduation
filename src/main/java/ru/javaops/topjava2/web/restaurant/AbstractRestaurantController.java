package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    RestaurantRepository repository;

    @Autowired
    MenuRepository menuRepository;


}
