package com.alllinkshare.catalog.repos;

import android.util.Log;

import com.alllinkshare.catalog.api.API;
import com.alllinkshare.catalog.api.config.Listeners;
import com.alllinkshare.catalog.models.Category;
import com.alllinkshare.catalog.ui.constants.LayoutStyle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CategoryRepository {
    private static final String TAG = "Repo/Category";

    private static final int ROOT_PARENT_ID = 0;

    private static List<Category> rootItems = new ArrayList<>();
    private static List<Category> recentItems = new ArrayList<>();

    private static CategoryRepository instance = null;
    private static CategoryCache categoryCache;

    public static synchronized CategoryRepository getInstance(){
        if(instance == null){
            instance = new CategoryRepository();
            categoryCache = new CategoryCache();
        }
        return instance;
    }

    private CategoryRepository(){
        Log.d(TAG, "New instance created...");
    }

    public void getCategories(int parentId, boolean loadAll, final DataReadyListener listener){
        Log.d(TAG, "Get categories for parent_id: "+parentId);

        if(parentId == ROOT_PARENT_ID && rootItems.size() > 0){
            listener.onDataReady(
                    ROOT_PARENT_ID,
                    "", "",
                    rootItems.size(),
                    LayoutStyle.GRID_WITH_THUMBNAILS,
                    rootItems
            );
        }
        else if(parentId != ROOT_PARENT_ID && parentId == categoryCache.getParentId() && !loadAll){
            listener.onDataReady(
                    categoryCache.getParentId(),
                    categoryCache.getParentName(),
                    categoryCache.getCoverImage(),
                    categoryCache.getChildCount(),
                    categoryCache.getLayoutStyle(),
                    categoryCache.getCategoryList()
            );
        }
        else{
            API.categories(parentId, loadAll, new Listeners.CategoriesListener() {
                @Override
                public void onSuccess(int parentId, String parentName, String coverImage, int childCount, String layoutStyle, List<Category> itemList) {
                    listener.onDataReady(
                            parentId,
                            parentName,
                            coverImage,
                            childCount,
                            layoutStyle,
                            itemList
                    );

                    if(parentId == ROOT_PARENT_ID){
                        rootItems.clear();
                        rootItems.addAll(itemList);
                    }
                    else{
                        addToRecent(parentId);
                        categoryCache.setData(parentId, parentName, coverImage, childCount, layoutStyle, itemList);
                    }
                }

                @Override
                public void onFailure(String error) {
                    listener.onFailure(error);
                }
            });
        }
    }

    private void addToRecent(int categoryId){
        for(Category category : rootItems){
            if(category.getId() == categoryId){
                for (Iterator<Category> iterator = recentItems.iterator(); iterator.hasNext(); ) {
                    Category value = iterator.next();
                    if (value.getId() == categoryId) {
                        iterator.remove();
                    }
                }
                recentItems.add(0, category);
            }
        }
    }

    public void getRecentCategories(DataReadyListener listener){
        List<Category> categoryList = recentItems;
        if(recentItems.size() > 9){
            categoryList = recentItems.subList(0, 8);
        }
        listener.onDataReady(
                0,
                "Recent",
                "null",
                9,
                LayoutStyle.GRID_WITH_THUMBNAILS,
                categoryList);
    }

    private static class CategoryCache {
        private int parentId;
        private String parentName;
        private String coverImage;
        private int childCount;
        private String layoutStyle;
        private List<Category> categoryList = new ArrayList<>();

        public void setData(int parentId, String parentName, String coverImage, int childCount, String layoutStyle, List<Category> itemList) {
            this.parentId = parentId;
            this.parentName = parentName;
            this.coverImage = coverImage;
            this.childCount = childCount;
            this.layoutStyle = layoutStyle;
            this.categoryList = itemList;
        }

        public int getParentId() {
            return parentId;
        }

        public String getParentName() {
            return parentName;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public int getChildCount() {
            return childCount;
        }

        public String getLayoutStyle() {
            return layoutStyle;
        }

        public List<Category> getCategoryList() {
            return categoryList;
        }
    }

    public interface DataReadyListener{
        void onDataReady(int parentId, String parentName, String coverImage, int childCount, String layoutStyle, List<Category> categoryList);
        void onFailure(String error);
    }
}