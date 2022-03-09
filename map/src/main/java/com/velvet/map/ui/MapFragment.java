package com.velvet.map.ui;

import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected MapContract.ViewModel createModel() {
        return new ViewModelProvider(this, new MapViewModelFactory(requireActivity())).get(MapViewModel.class);
    }

    @Override
    public void onDateSet(int year, int month, int dayOfMonth, String tag) {
        if (Values.START_TAG.equals(tag)) {
            getModel().updateFilter(DateFilter.createStartDateFilter(new GregorianCalendar(year, month - 1, dayOfMonth).getTime()));
            DatePickerFragment.newInstance(this, Values.END_TAG).show(requireActivity().getSupportFragmentManager(), "datePicker2");
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
        if (marker != null) {
            map.addMarker(marker);
        }
    }

    @Override
    public void postErrorMessage() {
        Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFilter(String startDate, String endDate) {
        Log.d("LOC", startDate + " startDate " + endDate + " endDate in FRAGMENT");
        binding.startDate.setText(getString(R.string.start_date, startDate));
        binding.endDate.setText(getString(R.string.end_date, endDate));
        map.clear();
    }

    @Override
    public void onClick(View v) {
        if (binding.filterButton == v) {
            DatePickerFragment.newInstance(this, Values.START_TAG).show(requireActivity().getSupportFragmentManager(), "datePicker1");
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
