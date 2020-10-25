package org.example.managers;

import org.example.restaurant.instance.Restaurant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class RestaurantManager {
    private static ArrayList<Restaurant> restaurants =
            new ArrayList<>(Arrays.asList(
                    new Restaurant("Miejska", "Sopot", "Chrobrego 33"),
                    new Restaurant("Wiejska", "Warszawa", "Wiejska 34"),
                    new Restaurant("Bogata", "Gdynia", "Swietojanska 55"),
                    new Restaurant("Zdrowa", "Gdansk", "Szeroka 11")));


    public static Optional<Restaurant> getRestaurant(String uuid) throws IOException {
        var restaurantToReturn = restaurants.stream().filter(restaurant -> restaurant.getId().toString().equals(uuid)).findFirst();
        return restaurantToReturn;
    }

    public static ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public static void addRestaurant(Restaurant res) throws IOException {
        restaurants.add(res);
    }

    public static boolean removeRestaurant(String uuid) throws IOException {
        var restaurantToRemove = restaurants.stream()
                .filter(restaurant -> restaurant.getId().toString().equals(uuid))
                .findFirst();
        if (restaurantToRemove.isPresent()) {
            AvatarsManager.removeAvatar(uuid);
            restaurants.remove(restaurantToRemove.get());
            return true;
        }
        return false;
    }

    public static boolean checkIfExists(String uuid) {
        var foundRestaurant = restaurants.stream().filter(restaurant -> restaurant.getId().toString().equals(uuid)).findFirst();
        return foundRestaurant.isPresent();
    }
}
