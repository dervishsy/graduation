package ru.javaops.topjava2.web.restaurants;

import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.web.MatcherFactory;

public class RestaurantsTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
    public static final int MCDONALDS_ID = 1;
    public static final Restaurant MCDONALDS = new Restaurant(MCDONALDS_ID, "McDonald's");
}
