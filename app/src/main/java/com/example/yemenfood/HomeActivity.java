package com.example.yemenfood;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yemenfood.databinding.ActivityHomeBinding;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    ActivityHomeBinding binding;
    ArrayList<Food> foodList;

    //ArrayList<Doctor> defaultDoctorList;
    RecyclerView recyclerView;
    FoodAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.btnHome.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.my_red)));
//        binding.btnCart.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.my_black)));
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        foodList=new ArrayList<>();
        recyclerView=binding.recyclerView;
        createFoodList();
        adapter = new FoodAdapter(this,foodList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        binding.btnAccount.setOnClickListener(v -> popUpMenu(binding.btnAccount));
        binding.btnCart.setOnClickListener(v -> {
            Intent intent= new Intent(getApplicationContext(), FoodCartActivity.class);
            intent.putExtra("ad",binding.boxMapAddress.getText().toString());
            startActivity(intent);
            finish();
        });
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filteredList(newText);
                return true;
            }
        });
        binding.boxMapAddress.setText(getIntent().getStringExtra("ad"));
        binding.btnMaps.setOnClickListener(v ->{
           startActivityForResult(new Intent(getApplicationContext(),MapsMarkerActivity.class),1);
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                if(!data.getStringExtra("address").trim().equals(""))
                    binding.boxMapAddress.setText(data.getStringExtra("address"));
            }
        }
    }


    private void popUpMenu(View v) {
        PopupMenu popup = new PopupMenu(getApplicationContext(),v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.user_icon_menu);
        popup.show();
    }

    private void createFoodList() {
        foodList.add(new Food("Fahsa", "Meat, bread, broth, spices", "https://www.chefspencil.com/wp-content/uploads/Fahsa-960x720.jpg.webp", 11.99f, 4.4f, 1, 15,"android.resource://" + getPackageName() + "/" + R.raw.fasah));
        foodList.add(new Food("Saltah", "Meat, fenugreek, vegetables, spices", "https://www.chefspencil.com/wp-content/uploads/Saltah-960x960.jpg.webp", 10.99f, 4.5f, 1, 50,"android.resource://" + getPackageName() + "/" + R.raw.saltah));
        foodList.add(new Food("Zurbian", "Rice, meat, tomatoes, spices", "https://www.chefspencil.com/wp-content/uploads/Zurbian-960x540.jpg.webp", 11.99f, 4.3f, 1, 30,"android.resource://" + getPackageName() + "/" + R.raw.zurbian));
        foodList.add(new Food("Mutabbaq", "Dough, meat, vegetables, spices", "https://www.chefspencil.com/wp-content/uploads/Mutabaq.jpg.webp", 8.99f, 4.3f, 1, 18,"android.resource://" + getPackageName() + "/" + R.raw.mutabbq));
        foodList.add(new Food("Mandi", "Rice, meat, spices", "https://www.chefspencil.com/wp-content/uploads/Mandi--960x960.jpg.webp", 12.99f, 4.8f, 1, 40,"android.resource://" + getPackageName() + "/" + R.raw.mandi));
        foodList.add(new Food("Fahsa", "Meat, bread, broth, spices", "https://www.chefspencil.com/wp-content/uploads/Fahsa-960x720.jpg.webp", 11.99f, 4.4f, 1, 15,"android.resource://" + getPackageName() + "/" + R.raw.fasah));
        foodList.add(new Food("Saltah", "Meat, fenugreek, vegetables, spices", "https://www.chefspencil.com/wp-content/uploads/Saltah-960x960.jpg.webp", 10.99f, 4.5f, 1, 50,"android.resource://" + getPackageName() + "/" + R.raw.saltah));
        foodList.add(new Food("Zurbian", "Rice, meat, tomatoes, spices", "https://www.chefspencil.com/wp-content/uploads/Zurbian-960x540.jpg.webp", 11.99f, 4.3f, 1, 30,"android.resource://" + getPackageName() + "/" + R.raw.zurbian));
        foodList.add(new Food("Mutabbaq", "Dough, meat, vegetables, spices", "https://www.chefspencil.com/wp-content/uploads/Mutabaq.jpg.webp", 8.99f, 4.3f, 1, 18,"android.resource://" + getPackageName() + "/" + R.raw.mutabbq));
        foodList.add(new Food("Mandi", "Rice, meat, spices", "https://www.chefspencil.com/wp-content/uploads/Mandi--960x960.jpg.webp", 12.99f, 4.8f, 1, 40,"android.resource://" + getPackageName() + "/" + R.raw.mandi));
        foodList.add(new Food("Fahsa", "Meat, bread, broth, spices", "https://www.chefspencil.com/wp-content/uploads/Fahsa-960x720.jpg.webp", 11.99f, 4.4f, 1, 15,"android.resource://" + getPackageName() + "/" + R.raw.fasah));
        foodList.add(new Food("Saltah", "Meat, fenugreek, vegetables, spices", "https://www.chefspencil.com/wp-content/uploads/Saltah-960x960.jpg.webp", 10.99f, 4.5f, 1, 50,"android.resource://" + getPackageName() + "/" + R.raw.saltah));
        foodList.add(new Food("Zurbian", "Rice, meat, tomatoes, spices", "https://www.chefspencil.com/wp-content/uploads/Zurbian-960x540.jpg.webp", 11.99f, 4.3f, 1, 30,"android.resource://" + getPackageName() + "/" + R.raw.zurbian));
        foodList.add(new Food("Mutabbaq", "Dough, meat, vegetables, spices", "https://www.chefspencil.com/wp-content/uploads/Mutabaq.jpg.webp", 8.99f, 4.3f, 1, 18,"android.resource://" + getPackageName() + "/" + R.raw.mutabbq));
        foodList.add(new Food("Mandi", "Rice, meat, spices", "https://www.chefspencil.com/wp-content/uploads/Mandi--960x960.jpg.webp", 12.99f, 4.8f, 1, 40,"android.resource://" + getPackageName() + "/" + R.raw.mandi));
        }
    private void filteredList(String txt){
        ArrayList<Food> filteredList = new ArrayList<>();
        for (Food food : foodList) {
            if (food.getName().toLowerCase().contains(txt.toLowerCase())||
                    food.getIngredients().toLowerCase().contains(txt.toLowerCase())) {
                filteredList.add(food);
            }
        }
        filteredList.sort((h1, h2) -> Float.compare(h2.getRating(), h1.getRating()));
        if (filteredList.isEmpty()) {
            adapter.setFilteredList(filteredList);
        } else {
            adapter.setFilteredList(filteredList);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.popupMenu_SignOut_btn) {
            //Toast.makeText(this, "SignOut", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
}