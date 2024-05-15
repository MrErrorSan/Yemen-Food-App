package com.example.yemenfood;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yemenfood.databinding.ActivityFoodCartBinding;

import java.util.ArrayList;

public class FoodCartActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener  {

    ActivityFoodCartBinding binding;
    ArrayList<Food> cartFoodList;
    //ArrayList<Doctor> defaultDoctorList;
    RecyclerView recyclerView;
    CartFoodAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFoodCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        cartFoodList=new ArrayList<>();
        recyclerView=binding.recyclerView;
        createCartFoodList();
        adapter = new CartFoodAdapter(this,cartFoodList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        binding.boxMapAddress.setText(getIntent().getStringExtra("ad"));
        binding.btnHome.setOnClickListener(v -> {
            Intent intent= new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("ad",binding.boxMapAddress.getText().toString());
            startActivity(intent);
            finish();
        });
        binding.btnAccount.setOnClickListener(v -> popUpMenu(binding.btnAccount));
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

    private void createCartFoodList() {
        cartFoodList.add(new Food("Fahsa", "Meat, bread, broth, spices", "https://www.chefspencil.com/wp-content/uploads/Fahsa-960x720.jpg.webp", 11.99f, 4.4f, 1, 15,"android.resource://" + getPackageName() + "/" + R.raw.fasah));
        cartFoodList.add(new Food("Saltah", "Meat, fenugreek, vegetables, spices", "https://www.chefspencil.com/wp-content/uploads/Saltah-960x960.jpg.webp", 10.99f, 4.5f, 1, 50,"android.resource://" + getPackageName() + "/" + R.raw.saltah));
        cartFoodList.add(new Food("Zurbian", "Rice, meat, tomatoes, spices", "https://www.chefspencil.com/wp-content/uploads/Zurbian-960x540.jpg.webp", 11.99f, 4.3f, 1, 30,"android.resource://" + getPackageName() + "/" + R.raw.zurbian));
        cartFoodList.add(new Food("Mutabbaq", "Dough, meat, vegetables, spices", "https://www.chefspencil.com/wp-content/uploads/Mutabaq.jpg.webp", 8.99f, 4.3f, 1, 18,"android.resource://" + getPackageName() + "/" + R.raw.mutabbq));
        cartFoodList.add(new Food("Mandi", "Rice, meat, spices", "https://www.chefspencil.com/wp-content/uploads/Mandi--960x960.jpg.webp", 12.99f, 4.8f, 1, 40,"android.resource://" + getPackageName() + "/" + R.raw.mandi));
    }

    private void popUpMenu(View v) {
        PopupMenu popup = new PopupMenu(getApplicationContext(),v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.user_icon_menu);
        popup.show();
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