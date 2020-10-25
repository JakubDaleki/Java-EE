package org.example.restaurant.view;

import lombok.Getter;
import lombok.Setter;
import org.example.managers.RestaurantManager;
import org.example.restaurant.instance.Restaurant;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@SessionScoped
@Named
public class RestaurantView implements Serializable {

    @Setter
    @Getter
    private String id;

    @Getter
    private Restaurant restaurant;

    public void init() throws IOException {
        Optional<Restaurant> restaurant = RestaurantManager.getRestaurant(id);
        if (restaurant.isPresent()) {
            this.restaurant = restaurant.get();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Restaurant not found");
        }
    }

    public String deleteAction(String uuid) {
        restaurant.removeMeal(uuid);
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
