package org.example.meal.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.managers.RestaurantManager;
import org.example.meal.instance.Meal;
import org.example.restaurant.instance.Restaurant;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

@ViewScoped
@Named
@NoArgsConstructor
public class MealAdd implements Serializable {
    @Setter
    @Getter
    private Restaurant restaurant;

    @Getter
    private ArrayList<Restaurant> restaurants;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String category;

    @Setter
    @Getter
    private float price;


    public void init() throws IOException {
        restaurants = RestaurantManager.getRestaurants();
    }

    public String addAction() throws IOException {
        restaurant.addMeal(new Meal(name, category, price));
        return "/restaurants/restaurant_view.xhtml?id=" + restaurant.getId() + "&faces-redirect=true&includeViewParams=true";
    }
}
