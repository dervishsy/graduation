package ru.javaops.topjava2.web.votes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.util.DateTimeUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;
import ru.javaops.topjava2.web.MatcherFactory;
import ru.javaops.topjava2.web.SecurityUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.util.JsonUtil.writeValue;
import static ru.javaops.topjava2.web.user.UserTestData.USER_MAIL;
import static ru.javaops.topjava2.web.votes.VoteTestData.MCDONALDS_ID;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = UserVoteController.REST_URL + '/';

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        MatcherFactory.Matcher<Vote> voteMatcher = MatcherFactory.usingEqualsComparator(Vote.class);

        Vote expected = new Vote(1, restaurantRepository.getById(MCDONALDS_ID),
                SecurityUtil.authUser(),
                DateTimeUtil.getCurrentDate());

        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(voteMatcher.contentJson(expected));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        Vote expected = new Vote(restaurantRepository.getById(MCDONALDS_ID),
                SecurityUtil.authUser(),
                DateTimeUtil.getCurrentDate());

        perform(MockMvcRequestBuilders.post(REST_URL + MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());

    }
}