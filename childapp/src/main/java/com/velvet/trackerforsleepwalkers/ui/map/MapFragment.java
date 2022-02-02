package com.velvet.trackerforsleepwalkers.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
