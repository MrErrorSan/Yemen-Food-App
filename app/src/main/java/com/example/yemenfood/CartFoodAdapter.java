package com.example.yemenfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.yemenfood.databinding.CartFoodListItemBinding;

import java.util.ArrayList;

public class CartFoodAdapter extends RecyclerView.Adapter<CartFoodAdapter.CartFoodViewHolder> {
    Context context;
    ArrayList<Food> foodList;

    public CartFoodAdapter(Context context, ArrayList<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }
    @NonNull
    @Override
    public CartFoodAdapter.CartFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_food_list_item, parent, false);
        return new CartFoodViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CartFoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.binding.boxFoodName.setText(food.getName());
        holder.binding.boxItems.setText(food.getIngredients());
        holder.binding.boxPrice.setText(food.getPrice()+" RM");
        RequestOptions requestOptions = new RequestOptions().transform(new CenterCrop(), new RoundedCorners(30)).placeholder(R.drawable.logo);
        Glide.with(context).load(food.getImage()).apply(requestOptions).into(holder.binding.img);
//        holder.itemView.setOnClickListener(view -> {
//            Intent intent= new Intent(context,FoodDetailsActivity.class);
//
//            try {
//                intent.putExtra("name", food.getName());
//                intent.putExtra("img", food.getImage());
//                intent.putExtra("ingredients", food.getIngredients());
//                intent.putExtra("price", food.getPrice());
//                context.startActivity(intent);
//            } catch (ActivityNotFoundException e) {
//                // Define what your app should do if no activity can handle the intent.
//                //holder.binding.boxFoodName.setText("FUNCTION NOT SUPPORTED ON THIS DEVICE");
//                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void setFilteredList(ArrayList<Food> filteredList) {
        this.foodList=filteredList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return foodList.size();
    }
    public static class CartFoodViewHolder extends RecyclerView.ViewHolder {
        CartFoodListItemBinding binding;

        public CartFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CartFoodListItemBinding.bind(itemView);
        }
    }
}


