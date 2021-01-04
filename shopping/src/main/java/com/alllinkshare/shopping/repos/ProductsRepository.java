package com.alllinkshare.shopping.repos;

import com.alllinkshare.shopping.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductsRepository {
    private static String[] images = {"a1", "a2", "a3", "a4"};
    private static String[] names = {"Daehan Electronics", "Daeyang Fiber", "Shinyang Oxygen Industry Company", "Kora-Japan Export Promotion Co. Ltd.", "Asia Trust Co. Ltd.", "Guangdong Pharmaceutial Co. Ltd.", "KIM Co., Ltd."};
    private static boolean[] bools = {true, false};
    private static float[] ratings = {4.5f, 3.7f, 5f, 4f};
    private static int[] ratingCounts = {23, 167, 10, 78, 315};
    private static double[] prices = {2400, 5900, 3400, 6750, 2550};
    private static List<Product> items = new ArrayList<>();

    public static List<Product> getFakeProducts(int count){
        items.clear();

        for(int i = 0; i < count; i++){
            Product product = new Product();
            product.setId(i+1);
            product.setName(names[new Random().nextInt(names.length)]);
            product.setThumbnail(images[new Random().nextInt(images.length)]);
            product.setPrice(prices[new Random().nextInt(prices.length)]);
            product.setRating(ratings[new Random().nextInt(ratings.length)]);
            product.setRatingCount(ratingCounts[new Random().nextInt(ratingCounts.length)]);
            items.add(product);
        }

        return items;
    }
}