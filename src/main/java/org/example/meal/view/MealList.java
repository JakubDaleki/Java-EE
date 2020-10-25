package org.example.meal.view;

import lombok.Getter;
import lombok.Setter;
import org.example.managers.RestaurantManager;
import org.example.meal.instance.Meal;
import org.example.restaurant.instance.Restaurant;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

/**
 * View bean for rendering list of characters.
 */
@RequestScoped
@Named
public class MealList implements Serializable {

    @Setter
    @Getter
    private String id;

    private ArrayList<Meal> meals;

    public ArrayList<Meal> getMeals() {
        try {
            Optional<Restaurant> restaurant = RestaurantManager.getRestaurant(id);
            if (restaurant.isPresent()) {
                this.meals = restaurant.get().getMenu();
                return this.meals;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<Meal>();
    }

    public String deleteAction(String uuid) throws IOException {
        System.out.println("Asasdasdsadsads");
        var mealToRemove = meals.stream()
                .filter(meal -> meal.getId().toString().equals(uuid))
                .findFirst();
        if (mealToRemove.isPresent()) {
            meals.remove(mealToRemove.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Restaurant not found");
        }
        return"restaurant_view?faces-redirect=true?id="+id;

    }


}
