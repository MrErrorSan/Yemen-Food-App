package com.example.yemenfood;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.yemenfood.databinding.ActivityLoginBinding;

import java.io.IOException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private Geocoder geocoder;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    Intent intent ;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Location
        geocoder = new Geocoder(this);
        intent  = new Intent(LoginActivity.this, HomeActivity.class);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                getAddressFromLatLng(latitude, longitude);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            intent.putExtra("ad","Select Location from Map");
        }




        myDB = new DBHelper(this);

        RequestOptions requestOptions = new RequestOptions().transform(new CenterCrop(), new RoundedCorners(30)).placeholder(R.drawable.register_bg);
        Glide.with(this).load(R.drawable.logo).apply(requestOptions).into(binding.imageView3);
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
        binding.btnLogin.setOnClickListener(v ->{
            String user = binding.editTextText2.getText().toString().trim();
            String pass = binding.editTextTextPassword2.getText().toString().trim();

            if(user.equals("") || pass.equals(""))
                Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            else{
                if(myDB.login(user, pass)){
                    Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
            myDB.displayAllUsers();
        });
        binding.btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(intent);
            finish();
        });
        binding.btnForgot.setOnClickListener(v ->{
            if(!binding.editTextText2.getText().toString().trim().equals(""))
            {
                if(myDB.checkUsername(binding.editTextText2.getText().toString()) || myDB.checkEmail(binding.editTextText2.getText().toString()))
                {
                    Intent intent = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
                    intent.putExtra("user",binding.editTextText2.getText().toString());
                    intent.putExtra("password",myDB.getPasswordByUsernameOrEmail(binding.editTextText2.getText().toString().trim()));
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Username or Email does not exists",Toast.LENGTH_SHORT).show();
                    binding.editTextText2.setError("Email or username does not exists");
                }
            }else{
                Toast.makeText(getApplicationContext(),"Enter Username",Toast.LENGTH_SHORT).show();
                binding.editTextText2.setError("Enter Username or Email to get password");
            }
            //Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
        });
    }
    private void getAddressFromLatLng(double latitude, double longitude) {
        String address = "Select Location from Map";

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);
                StringBuilder addressBuilder = new StringBuilder();
                for (int i = 0; i <= fetchedAddress.getMaxAddressLineIndex(); i++) {
                    addressBuilder.append(fetchedAddress.getAddressLine(i));
                    if (i < fetchedAddress.getMaxAddressLineIndex()) {
                        addressBuilder.append(", ");
                    }
                }
                address = addressBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        intent.putExtra("ad",address);
    }
}