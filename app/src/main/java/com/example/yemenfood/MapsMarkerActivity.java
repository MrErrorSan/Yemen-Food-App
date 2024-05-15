package com.example.yemenfood;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private SearchView searchView;
    private ProgressDialog progressDialog;
    private CircleOptions currentLocationCircleOptions;
    private CircleOptions customMarkerCircleOptions;
    private Marker customMarker;
    Intent intent = new Intent();
    String a="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        geocoder = new Geocoder(this);
        searchView = findViewById(R.id.searchView);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setupSearchView();


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Searching...");
        progressDialog.setCanceledOnTouchOutside(false);

        ImageView backButton = findViewById(R.id.btnBack1);
        backButton.setOnClickListener(v -> {
            intent.putExtra("address", a);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
    @Override
    public void onBackPressed()
    {
        intent.putExtra("address", a);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                moveMarkerToAddress(query);
                hideKeyboard();
                return true;
            }
            private void hideKeyboard() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                View currentFocus = getCurrentFocus();
                if (currentFocus != null) {
                    imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                    currentFocus.clearFocus();
                }
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        // Move the location button to the bottom right corner
        assert mapFragment != null;
        View locationButton = ((View) mapFragment.requireView().findViewById(Integer.parseInt("1")).getParent())
                .findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.setMargins(0, 0, 30, 30);

        // Move the compass button to the bottom left corner
        View compassButton = ((View) mapFragment.requireView().findViewById(Integer.parseInt("1")).getParent())
                .findViewById(Integer.parseInt("5"));
        RelativeLayout.LayoutParams compassLayoutParams = (RelativeLayout.LayoutParams) compassButton.getLayoutParams();
        compassLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        compassLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        compassLayoutParams.setMargins(30, 0, 0, 30);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                moveAndAddCurrentLocationMarker(latitude, longitude);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        mMap.setOnMapLongClickListener(this::addCustomMarker);
    }
    private void moveAndAddCurrentLocationMarker(double latitude, double longitude) {
        LatLng location = new LatLng(latitude, longitude);

        if (currentLocationCircleOptions != null) {
            currentLocationCircleOptions = null;
        }

        currentLocationCircleOptions = new CircleOptions()
                .center(location)
                .radius(30)
                .strokeColor(Color.BLUE)
                .fillColor(Color.argb(128, 0, 0, 255));

//        currentLocationMarker = mMap.addMarker(new MarkerOptions()
//                .position(location)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f));
    }
    private void moveMarkerToAddress(String address) {
        progressDialog.show();

        new GeocodeAsyncTask().execute(address);
    }

    private class GeocodeAsyncTask extends AsyncTask<String, Void, LatLng> {
        @Override
        protected LatLng doInBackground(String... addresses) {
            String address = addresses[0];
            try {
                List<Address> addressesList = geocoder.getFromLocationName(address, 1);
                if (!addressesList.isEmpty()) {
                    Address targetAddress = addressesList.get(0);
                    a=targetAddress.toString();
                    double latitude = targetAddress.getLatitude();
                    double longitude = targetAddress.getLongitude();
                    return new LatLng(latitude, longitude);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(LatLng latLng) {
            if (latLng != null) {
                addCustomMarker(latLng);
            } else {
                Toast.makeText(MapsMarkerActivity.this, "Failed to geocode address", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    }

    private void addCustomMarker(LatLng latLng) {
        String address = getAddressFromLatLng(latLng);
        a=address;
        if (customMarker != null) {
            customMarker.remove();
        }
        if (customMarkerCircleOptions != null) {
            customMarkerCircleOptions = null;
        }

        customMarkerCircleOptions = new CircleOptions()
                .center(latLng)
                .radius(10)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(128, 255, 0, 0));

        customMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(address)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private String getAddressFromLatLng(LatLng latLng) {
        String address = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
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
        a=address;
        return address;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(mMap);
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
