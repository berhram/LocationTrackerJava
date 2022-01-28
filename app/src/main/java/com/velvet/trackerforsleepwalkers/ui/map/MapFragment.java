package com.velvet.trackerforsleepwalkers.ui.map;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.trackerforsleepwalkers.R;
import com.velvet.trackerforsleepwalkers.databinding.FragmentMapBinding;
import com.velvet.trackerforsleepwalkers.location.LocationService;
import com.velvet.trackerforsleepwalkers.mvi.HostedFragment;


public class MapFragment extends HostedFragment<MapViewState,
        MapContract.ViewModel,
        MapContract.Host,
        MapViewEffect,
        MapContract.View> implements MapContract.View,
        View.OnClickListener,
        OnMapReadyCallback {
    private FragmentMapBinding binding;
    private GoogleMap map;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected MapContract.ViewModel createModel() {
        //why
        return new ViewModelProvider(this, (ViewModelProvider.Factory) new MapViewModelFactory()).get(MapViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                    if (!result.get(Manifest.permission.ACCESS_FINE_LOCATION) || !result.get(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                        Toast.makeText(getContext(), R.string.please_give_access, Toast.LENGTH_SHORT).show();
                    }
                });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION});
        } else {
            locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
        }
        Intent intent = new Intent(getContext(), LocationService.class);
        getContext().startService(intent);
    }

    @Override
    public void setMarkers() {

    }

    @Override
    public void createFilter() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void proceedToSettingsScreen() {
        if (hasHost()) {
            getFragmentHost().proceedToSettingsScreen("Map");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
