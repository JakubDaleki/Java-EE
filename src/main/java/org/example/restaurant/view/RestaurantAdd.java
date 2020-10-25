package org.example.restaurant.view;

import lombok.Getter;
import lombok.Setter;
import org.example.managers.RestaurantManager;
import org.example.restaurant.instance.Restaurant;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@ViewScoped
@Named
public class RestaurantAdd implements Serializable {
    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String city;

    @Setter
    @Getter
    private String address;

    public void init() throws IOException {

    }

    public String addAction() throws IOException {
        RestaurantManager.addRestaurant(new Restaurant(name, city, address));
        return "restaurant_list?faces-redirect=true";
    }
}
