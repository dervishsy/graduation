package ru.javaops.topjava2.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.MenuItem;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.util.DateTimeUtil;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.util.JsonUtil.writeValue;
import static ru.javaops.topjava2.web.menu.MenuTestData.*;
import static ru.javaops.topjava2.web.restaurants.RestaurantsTestData.MCDONALDS;
import static ru.javaops.topjava2.web.restaurants.RestaurantsTestData.MCDONALDS_ID;
import static ru.javaops.topjava2.web.user.UserTestData.ADMIN_MAIL;

class AdminMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminMenuController.REST_URL + '/';

    @Autowired
    private MenuRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + BIG_MAC_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(BIG_MAC));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + BIG_MAC_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(BIG_MAC_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        MenuItem newMenuItem = SAUSAGE_BURRITO;
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMenuItem)))
                .andExpect(status().isCreated());

        MenuItem created = MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenuItem.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenuItem);
        MENU_MATCHER.assertMatch(repository.getById(newId), newMenuItem);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        MenuItem updated = new MenuItem(null, "Very Big Mac", MCDONALDS, DateTimeUtil.getCurrentDate(), new BigDecimal("200.20"));
        perform(MockMvcRequestBuilders.put(REST_URL + BIG_MAC_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        updated.setId(BIG_MAC_ID);
        MENU_MATCHER.assertMatch(repository.getById(BIG_MAC_ID), updated);
    }
}