package com.alllinkshare.shopping.repos;


import com.alllinkshare.shopping.models.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoriesRepository {
    private static String[] icons = {"image1", "image2", "image3", "image4", "image5", "image6", "image7"};
    private static String[] images = {"a1", "a2", "a3", "a4"};
    private static String[] names = {"Education", "Shopping", "Restaurants", "Games", "Baby care", "Auto mobiles", "Service booking"};
    private static String[] descriptions = {"Try it yourself", "Order now", null, null};
    private static boolean[] bools = {true, false};
    private static List<Category> items = new ArrayList<>();

    public static List<Category> getFakeCategoriesWithIcons(int count, boolean allOptions){
        items.clear();

        if(allOptions){
            Category allOptionsCategory = new Category();
            allOptionsCategory.setId(0);
            allOptionsCategory.setName("All options");
            allOptionsCategory.setThumbnail("icon_open");
            allOptionsCategory.setHasSubCategories(false);
            items.add(allOptionsCategory);
        }

        for(int i = 0; i < count; i++){
            Category category = new Category();
            category.setId(i+1);
            category.setName(names[new Random().nextInt(names.length)]);
            category.setThumbnail(icons[new Random().nextInt(icons.length)]);
            category.setHasSubCategories(bools[new Random().nextInt(bools.length)]);

            items.add(category);
        }

        return items;
    }

    public static List<Category> getFakeCategoriesWithImages(int count){
        items.clear();

        for(int i = 0; i < count; i++){
            Category category = new Category();
            category.setId(i+1);
            category.setName(names[new Random().nextInt(names.length)]);
            category.setDescription(descriptions[new Random().nextInt(descriptions.length)]);
            category.setThumbnail(images[new Random().nextInt(images.length)]);
            category.setHasSubCategories(bools[new Random().nextInt(bools.length)]);

            items.add(category);
        }

        return items;
    }
}