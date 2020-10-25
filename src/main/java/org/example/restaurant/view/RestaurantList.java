package org.example.restaurant.view;

import org.example.managers.RestaurantManager;
import org.example.restaurant.instance.Restaurant;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * View bean for rendering list of characters.
 */
@RequestScoped
@Named
public class RestaurantList implements Serializable {

    private ArrayList<Restaurant> restaurants;

    public ArrayList<Restaurant> getRestaurants() {

        return RestaurantManager.getRestaurants();
    }

    public String deleteAction(String uuid) {
        try {
            RestaurantManager.removeRestaurant(uuid);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        return "restaurant_list?faces-redirect=true";
    }

}
