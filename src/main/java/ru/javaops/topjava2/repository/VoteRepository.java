package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.User;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    @NonNull
    List<Vote> findAll();


    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Vote> findVoteByUserAndDate(User user, LocalDate date);

}
