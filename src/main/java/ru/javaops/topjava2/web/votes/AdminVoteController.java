package ru.javaops.topjava2.web.votes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.javaops.topjava2.util.validation.ValidationUtil.checkIsValidVoteTime;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "vote")
public class AdminVoteController extends AbstractVoteController {
    public static final String REST_URL = "/api/admin/votes";

    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@PathVariable int id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping
//  TODO  @Cacheable
    public List<Vote> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @PostMapping(value = "/{restaurantId}")
    public ResponseEntity<Vote> vote(@PathVariable int restaurantId) {
        LocalDate dateOfVote = LocalDate.now();
        Optional<Vote> vote = repository.findVoteByUserAndDate(SecurityUtil.authUser(), dateOfVote);
        if (vote.isPresent()) {
            checkIsValidVoteTime(dateOfVote);
        } else {
            vote = Optional.of(new Vote(restaurantRepository.getById(restaurantId), SecurityUtil.authUser(), dateOfVote));
        }

        Vote created = repository.save(vote.get());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
