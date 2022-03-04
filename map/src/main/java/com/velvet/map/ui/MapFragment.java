package com.velvet.map.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.core.Values;
import com.velvet.core.filter.DateFilter;
import com.velvet.map.R;
import com.velvet.map.databinding.FragmentMapBinding;
import com.velvet.map.ui.datepicker.DateListener;
import com.velvet.map.ui.datepicker.DatePickerFragment;
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
        OnMapReadyCallback,
        DateListener {

    private FragmentMapBinding binding;
    private GoogleMap map;
    private final DialogFragment startDatePicker = new DatePickerFragment(this, Values.START_TAG);
    private final DialogFragment endDatePicker = new DatePickerFragment(this, Values.END_TAG);

    @Override
    protected MapContract.ViewModel createModel() {
        return new ViewModelProvider(this, new MapViewModelFactory(requireActivity())).get(MapViewModel.class);
    }

    @Override
    public void onDateSet(int year, int month, int dayOfMonth, String tag) {
        if (Values.START_TAG.equals(tag)) {
            getModel().updateFilter(DateFilter.createStartDateFilter(new GregorianCalendar(year, month - 1, dayOfMonth).getTime()));
            endDatePicker.show(requireActivity().getSupportFragmentManager(), "datePicker");
        } else if (Values.END_TAG.equals(tag)) {
            getModel().updateFilter(DateFilter.createEndDateFilter(new GregorianCalendar(year, month - 1, dayOfMonth).getTime()));
        }
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
        binding.filterButton.setOnClickListener(this);
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
    public void setFilter(String startDate, String endDate) {
        binding.startDate.setText(getString(R.string.start_date, startDate));
        binding.endDate.setText(getString(R.string.end_date, endDate));
    }

    @Override
    public void onClick(View v) {
        if (binding.filterButton == v) {
            Log.d("LOC", "filter button clicked");
            startDatePicker.show(requireActivity().getSupportFragmentManager(), "datePicker");
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
