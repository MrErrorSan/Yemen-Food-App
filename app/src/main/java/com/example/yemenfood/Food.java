package com.example.yemenfood;

public class Food {
    String name, ingredients, image, video;
    float price, rating;
    int quantity, stock;

    public Food(String name, String ingredients, String image, float price, float rating, int quantity, int stock, String video) {
        this.name = name;
        this.ingredients = ingredients;
        this.image = image;
        this.price = price;
        this.rating = rating;
        this.quantity = quantity;
        this.stock = stock;
        this.video= video;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
