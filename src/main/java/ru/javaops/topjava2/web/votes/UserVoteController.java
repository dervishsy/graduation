package ru.javaops.topjava2.web.votes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.util.DateTimeUtil;
import ru.javaops.topjava2.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

import static ru.javaops.topjava2.util.validation.ValidationUtil.checkIsValidVoteTime;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "vote")
public class UserVoteController {
    public static final String REST_URL = "/api/votes";

    @Autowired
    VoteRepository repository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping()
    public Optional<Vote> get() {
        log.info("getCurrentVote");
        return repository.findVoteByUserAndDate(SecurityUtil.authUser(),DateTimeUtil.getCurrentDate());
    }

    @PutMapping(value = "/{restaurantId}")
    public ResponseEntity<Vote> change_vote(@PathVariable int restaurantId) {
        log.info("change_vote {} {}",SecurityUtil.authUser(),restaurantId);
        checkIsValidVoteTime();

        LocalDate dateOfVote = DateTimeUtil.getCurrentDate();

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()->new IllegalRequestDataException("Restaurant not found Id:"+restaurantId));

        Vote vote = repository.findVoteByUserAndDate(SecurityUtil.authUser(), dateOfVote)
                .orElseThrow(()->new IllegalRequestDataException("Vote not exist"));

        vote.setRestaurant(restaurant);
        Vote created = repository.save(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/{restaurantId}")
    public ResponseEntity<Vote> vote(@PathVariable int restaurantId) {
        log.info("vote {} {}",SecurityUtil.authUser(),restaurantId);
        LocalDate dateOfVote = DateTimeUtil.getCurrentDate();

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()->new IllegalRequestDataException("Restaurant not found Id:"+restaurantId));

        if (repository.findVoteByUserAndDate(SecurityUtil.authUser(), dateOfVote).isPresent()) {
            throw new IllegalRequestDataException("Vote for current day is exist");
        }

        Vote created = repository.save(new Vote(restaurant, SecurityUtil.authUser(), dateOfVote));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
