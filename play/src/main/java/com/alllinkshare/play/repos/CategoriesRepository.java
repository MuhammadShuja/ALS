package com.alllinkshare.play.repos;

import com.alllinkshare.play.models.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoriesRepository {
    private static String[] names = {"Education", "Shopping", "Restaurants", "Games", "Baby care", "Auto mobiles", "Service booking"};
    private static List<Category> items = new ArrayList<>();

    public static List<Category> getFakeCategories(int count){
        items.clear();

        for(int i = 0; i < count; i++){
            Category category = new Category(i+1, names[new Random().nextInt(names.length)]);

            items.add(category);
        }

        return items;
    }
}