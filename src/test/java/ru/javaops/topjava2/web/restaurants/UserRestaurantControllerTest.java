package ru.javaops.topjava2.web.restaurants;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.util.DateTimeUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;
import ru.javaops.topjava2.web.restaurant.UserRestaurantController;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.restaurants.RestaurantsTestData.MCDONALDS_ID;
import static ru.javaops.topjava2.web.restaurants.RestaurantsTestData.RESTAURANT_MATCHER;
import static ru.javaops.topjava2.web.user.UserTestData.USER_MAIL;

public class UserRestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL = UserRestaurantController.REST_URL + '/';

    @Autowired
    private RestaurantRepository repository;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllWithMenu() throws Exception {
        List<Restaurant> restaurants = repository.findAll();
        restaurants.forEach(r -> r.setMenu(menuRepository.getMenuItemByRestaurantIdAndDate(r.getId(), DateTimeUtil.getCurrentDate())));

        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurants));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithMenuById() throws Exception {
        Restaurant restaurant = repository.findById(MCDONALDS_ID).get();
        restaurant.setMenu(menuRepository.getMenuItemByRestaurantIdAndDate(MCDONALDS_ID, DateTimeUtil.getCurrentDate()));

        perform(MockMvcRequestBuilders.get(REST_URL+MCDONALDS_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant));
    }
}
