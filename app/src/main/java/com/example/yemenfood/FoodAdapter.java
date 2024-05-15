package com.example.yemenfood;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.yemenfood.databinding.FoodListItemBinding;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    Context context;
    ArrayList<Food> foodList;

    public FoodAdapter(Context context, ArrayList<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }
    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_list_item, parent, false);
        return new FoodViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.binding.boxFoodName.setText(food.getName());
        holder.binding.boxFoodPrice.setText(food.getPrice()+" RM");
        holder.binding.ratingBar.setRating(food.getRating());
        RequestOptions requestOptions = new RequestOptions().transform(new CenterCrop(), new RoundedCorners(30)).placeholder(R.drawable.logo);
        Glide.with(context).load(food.getImage()).apply(requestOptions).into(holder.binding.imgFood);
        holder.itemView.setOnClickListener(view -> {
            Intent intent= new Intent(context,FoodDetailsActivity.class);

            try {
                intent.putExtra("name", food.getName());
                intent.putExtra("img", food.getImage());
                intent.putExtra("video", food.getVideo());
                intent.putExtra("ingredients", food.getIngredients());
                intent.putExtra("price", food.getPrice());
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Define what your app should do if no activity can handle the intent.
                //holder.binding.boxFoodName.setText("FUNCTION NOT SUPPORTED ON THIS DEVICE");
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setFilteredList(ArrayList<Food> filteredList) {
        this.foodList=filteredList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return foodList.size();
    }
    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        FoodListItemBinding binding;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FoodListItemBinding.bind(itemView);
        }
    }
}


