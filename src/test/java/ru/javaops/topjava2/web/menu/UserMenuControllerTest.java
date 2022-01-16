package ru.javaops.topjava2.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.MenuItem;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.util.DateTimeUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.menu.MenuTestData.MENU_MATCHER;
import static ru.javaops.topjava2.web.restaurants.RestaurantsTestData.MCDONALDS_ID;
import static ru.javaops.topjava2.web.user.UserTestData.ADMIN_MAIL;

class UserMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserMenuController.REST_URL + '/';

    @Autowired
    private MenuRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        List<MenuItem> expected = repository.getMenuItemByRestaurantIdAndDate(MCDONALDS_ID, DateTimeUtil.getCurrentDate());

        perform(MockMvcRequestBuilders.get(REST_URL + MCDONALDS_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(expected));
    }
}