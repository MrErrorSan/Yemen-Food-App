package com.example.yemenfood;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.yemenfood.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myDB = new DBHelper(this);


        RequestOptions requestOptions = new RequestOptions().transform(new CenterCrop(), new RoundedCorners(30)).placeholder(R.drawable.register_bg);
        Glide.with(this).load(R.drawable.logo).apply(requestOptions).into(binding.imageView3);
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnRegister.setOnClickListener(v ->{
            String user = binding.editTextText2.getText().toString().trim();
            String email = binding.editTextTextEmailAddress2.getText().toString().trim();
            String pass = binding.editTextTextPassword2.getText().toString().trim();

            if(user.equals("") || pass.equals("") || email.equals("")){
                Toast.makeText(RegisterActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            }
            else{
                if(myDB.checkEmail(email) || myDB.checkUsername(user) )
                {
                    if(myDB.checkEmail(email)){
                        binding.editTextTextEmailAddress2.setError("Email already exists!");
                        Toast.makeText(RegisterActivity.this, "Email already exists! please sign in", Toast.LENGTH_SHORT).show();
                    }
                    if(myDB.checkUsername(user)){
                        binding.editTextText2.setError("User already exists!");
                        Toast.makeText(RegisterActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();;
                    }
                    return;
                }
                boolean insert = myDB.insertData(user, pass, email);
                if(insert){
                    Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
            myDB.displayAllUsers();
        });
    }
}