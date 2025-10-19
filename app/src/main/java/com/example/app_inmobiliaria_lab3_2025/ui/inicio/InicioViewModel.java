package com.example.app_inmobiliaria_lab3_2025.ui.inicio;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;



public class InicioViewModel extends AndroidViewModel {

    private MutableLiveData<Location> mLocation;
    private FusedLocationProviderClient fusedLocationProvider;

    //Constructor
    public InicioViewModel(@NonNull Application application) {
        super(application);
        fusedLocationProvider= LocationServices.getFusedLocationProviderClient(getApplication());
    }


    public MutableLiveData<Location> getMlocation() {
        if(mLocation==null){
            this.mLocation=new MutableLiveData<>();
        }
        return mLocation;
    }


    public void obtenerUbicacion() {
        if (ActivityCompat.checkSelfPermission(getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplication(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationProvider.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        mLocation.postValue(location);
                    }
                });
    }




}