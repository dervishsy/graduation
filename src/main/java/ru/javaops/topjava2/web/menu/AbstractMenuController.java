package ru.javaops.topjava2.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.topjava2.repository.MenuRepository;

@Slf4j
public abstract class AbstractMenuController {

    @Autowired
    MenuRepository repository;

}
