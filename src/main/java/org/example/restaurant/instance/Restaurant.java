package org.example.restaurant.instance;

import lombok.Data;
import org.example.meal.instance.Meal;

import java.util.*;

@Data
public class Restaurant {
    public Restaurant(String name, String city, String address) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.city = city;
        this.address = address;
        Random r = new Random();
        String[] meals = {"NALEŚNIKI ZE SZPARAGAMI I BOCZKIEM Z SOSEM HOLENDERSKIM",
                "PANCAKES Z CIECIERZYCY DLA NIEJ I DLA NIEGO",
                "MAKARON RYŻOWY ZE SZPARAGAMI I WOŁOWINĄ MARYNOWANĄ W MAŚLE ORZECHOWYM",
                "DOMOWY BUDYŃ ZE SMAŻONYMI BORÓWKAMI", "KREM Z PIECZONEGO TOPINAMBURU I KALAFIORA",
                "WEGAŃSKIE MAKARONIKI NA RÓŻOWO", "WEGAŃSKA BIAŁA PIZZA ZE ZIELONYMI SZPARAGAMI I DUSZONYM POREM",
                "PLACUSZKI Z RICOTTY Z MAKIEM I CYTRYNĄ"};
        String[] categories = {"Desery", "Obiady", "Ryby", "Miesne", "Slodkie", "Napoje", "Lody"};
        this.menu = new ArrayList<Meal>();

        for (int i = 0; i <= r.nextInt(20); i++) {
            this.menu.add(new Meal(meals[r.nextInt(meals.length)], categories[r.nextInt(categories.length)], r.nextFloat() * 100.0f));
        }
    }

    public boolean removeMeal(String id)
    {
        var mealToRemove = menu.stream()
                .filter(meal -> meal.getId().toString().equals(id))
                .findFirst();
        if (mealToRemove.isPresent()) {
            menu.remove(mealToRemove.get());
            return true;
        }
        return false;
    }

    public Optional<Meal> getMeal(String id)
    {
        var mealToReturn = menu.stream()
                .filter(meal -> meal.getId().toString().equals(id))
                .findFirst();
        return mealToReturn;
    }

    public void addMeal(Meal meal) {
        menu.add(meal);
    }

    private UUID id;
    private String name;
    private String city;
    private String address;
    private ArrayList<Meal> menu;
}
