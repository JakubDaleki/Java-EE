package org.example.restaurant.view;

import lombok.Getter;
import lombok.Setter;
import org.example.managers.RestaurantManager;
import org.example.restaurant.instance.Restaurant;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;


@ViewScoped
@Named
public class RestaurantEdit implements Serializable {
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

    public String saveAction() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
