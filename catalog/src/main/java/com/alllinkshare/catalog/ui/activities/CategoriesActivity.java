package com.alllinkshare.catalog.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.alllinkshare.catalog.R;
import com.alllinkshare.catalog.models.Category;
import com.alllinkshare.catalog.ui.adapters.CategoryListWithIconsAdapter;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    private CategoryListWithIconsAdapter categoryListWithIconsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_categories);

        initToolbar();
        initCategories();
        loadCategories();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolbar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg));
        }
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorThemeDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.title)).setText("Categories");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initCategories(){
        categoryListWithIconsAdapter = new CategoryListWithIconsAdapter(this, new ArrayList<Category>(), new CategoryListWithIconsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
//                if(getIntent().getExtras().getBoolean("full_demo")){
//                    Intent intent = new Intent(CategoriesActivity.this, CatalogActivity.class);
//                    intent.putExtra("fragment", "CategoryFragment");
//                    intent.putExtra("full_demo", true);
//                    intent.putExtra("category_id", category.getId());
//                    startActivity(intent);
//                }
//                Intent intent = new Intent(CategoriesActivity.this, CatalogActivity.class);
//                intent.putExtra("fragment", "CategoryFragment");
//                intent.putExtra("full_demo", true);
//                intent.putExtra("category_id", category.getId());
//                startActivity(intent);
            }
        });

        final RecyclerView rvItems = findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(categoryListWithIconsAdapter);
    }

    private void loadCategories(){
//        categoriesWithIconAdapter.setData(CategoryRepository.getInstance().getFakeCategoriesWithIcons(30, false));
    }
}