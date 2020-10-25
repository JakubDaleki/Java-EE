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
import java.util.Optional;

@RequestScoped
@Named
public class MealView implements Serializable {

    @Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private String restaurantId;

    @Getter
    private Meal meal;

    public void init() throws IOException {
        Optional<Restaurant> restaurant = RestaurantManager.getRestaurant(restaurantId);
        if (restaurant.isPresent()) {
            Optional<Meal> meal = restaurant.get().getMeal(id);
            if (meal.isPresent()) {
                this.meal = meal.get();
                return;
            }
        }
        FacesContext.getCurrentInstance().getExternalContext()
                .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Meal not found");
    }


}
