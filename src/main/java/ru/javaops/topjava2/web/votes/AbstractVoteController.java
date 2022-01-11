package ru.javaops.topjava2.web.votes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;

@Slf4j
public abstract class AbstractVoteController {

    @Autowired
    VoteRepository repository;

    @Autowired
    RestaurantRepository restaurantRepository;
}
