package org.example.meal.instance;

import lombok.*;
import org.example.user.instance.User;

import java.util.ArrayList;
import java.util.UUID;

@Data
public class Meal {
    public Meal(String name, String category, float price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.category = category;
        this.price = price;
        this.lovers = new ArrayList<User>();
        this.haters = new ArrayList<User>();
    }

    private UUID id;
    private String name;
    private String category;
    private float price;
    private ArrayList<User> lovers;
    private ArrayList<User> haters;
}
