package ru.javaops.topjava2.web.menu;

import ru.javaops.topjava2.model.MenuItem;
import ru.javaops.topjava2.util.DateTimeUtil;
import ru.javaops.topjava2.web.MatcherFactory;

import java.math.BigDecimal;

import static ru.javaops.topjava2.web.restaurants.RestaurantsTestData.MCDONALDS;

public class MenuTestData {
    public static final int BIG_MAC_ID = 1;
    public static final MenuItem BIG_MAC = new MenuItem(1, "Big Mac", MCDONALDS, DateTimeUtil.getCurrentDate(), new BigDecimal(200));
    public static final MenuItem SAUSAGE_BURRITO = new MenuItem(null, "Big Mac", MCDONALDS, DateTimeUtil.getCurrentDate(), new BigDecimal(200));
    public static final int NOT_FOUND = 999;
    public static final MatcherFactory.Matcher<MenuItem> MENU_MATCHER = MatcherFactory.usingEqualsComparator(MenuItem.class);
}
