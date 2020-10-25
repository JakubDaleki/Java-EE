package org.example.restaurant.converter;

import org.example.managers.RestaurantManager;
import org.example.restaurant.instance.Restaurant;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean
@RequestScoped
@FacesConverter(forClass = Restaurant.class, managed = true)
public class RestaurantConverter implements Converter<Restaurant> {

    @Override
    public Restaurant getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        return RestaurantManager.getRestaurants().stream()
                .filter(res -> res.getName().equals(s))
                .findFirst().orElse(null);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Restaurant restaurant) {
        return restaurant == null ? "" : restaurant.getName();
    }
}
