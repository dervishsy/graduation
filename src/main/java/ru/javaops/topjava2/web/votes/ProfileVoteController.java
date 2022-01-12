package ru.javaops.topjava2.web.votes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "vote")
public class ProfileVoteController {
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

    @PostMapping(value = "/{restaurantId}")
    public ResponseEntity<Vote> vote(@PathVariable int restaurantId) {
        LocalDate dateOfVote = DateTimeUtil.getCurrentDate();
        Optional<Vote> vote = repository.findVoteByUserAndDate(SecurityUtil.authUser(), dateOfVote);
        if (vote.isPresent()) {
            checkIsValidVoteTime(dateOfVote);
        } else {
            Restaurant restaurant = restaurantRepository.getById(restaurantId);
            vote = Optional.of(new Vote(restaurant, SecurityUtil.authUser(), dateOfVote));
        }

        Vote created = repository.save(vote.get());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
