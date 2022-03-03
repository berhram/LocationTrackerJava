package com.velvet.map.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.core.Values;
import com.velvet.core.filter.DateFilter;
import com.velvet.map.R;
import com.velvet.map.databinding.FragmentMapBinding;
import com.velvet.map.ui.state.MapViewEffect;
import com.velvet.map.ui.state.MapViewState;
import com.velvet.libs.mvi.HostedFragment;

import java.util.GregorianCalendar;

public class MapFragment extends HostedFragment<MapViewState,
        MapContract.ViewModel,
        MapContract.Host,
        MapViewEffect,
        MapContract.View> implements MapContract.View,
        View.OnClickListener,
        OnMapReadyCallback {

    private FragmentMapBinding binding;
    private GoogleMap map;
    DatePickerDialog startDatePickerDialog = new DatePickerDialog(requireActivity());
    DatePickerDialog endDatePickerDialog = new DatePickerDialog(requireActivity());

    @Override
    protected MapContract.ViewModel createModel() {
        return new ViewModelProvider(this, new MapViewModelFactory(requireActivity())).get(MapViewModel.class);
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
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        startDatePickerDialog.setOnDateSetListener((view12, year, month, dayOfMonth) -> {
            getModel().updateFilter(DateFilter.createStartDateFilter(new GregorianCalendar(year, month - 1, dayOfMonth).getTime()));
            endDatePickerDialog.show();
        });
        endDatePickerDialog.setOnDateSetListener((view1, year, month, dayOfMonth) -> getModel().updateFilter(DateFilter.createEndDateFilter(new GregorianCalendar(year, month - 1, dayOfMonth).getTime())));
    }

    @Override
    public void setMarker(MarkerOptions marker) {
        map.addMarker(marker);
    }

    @Override
    public void postErrorMessage() {
        Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (binding.filterButton == v) {
            startDatePickerDialog.show();
        }
    }

    @Override
    public void proceedToLoginScreen() {
        if (hasHost()) {
            getFragmentHost().proceedToLoginScreen(Values.MAP_NAV);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        getModel().mapReadyCallback();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
