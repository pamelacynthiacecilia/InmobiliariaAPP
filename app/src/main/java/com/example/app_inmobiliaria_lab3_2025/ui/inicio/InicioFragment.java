package com.example.app_inmobiliaria_lab3_2025.ui.inicio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.content.ContextCompat;

import com.example.app_inmobiliaria_lab3_2025.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioFragment extends Fragment implements OnMapReadyCallback {

    private InicioViewModel inicioViewModel;
    private GoogleMap mMap;
    private TextView tvDistancia;

    // Coordenadas fijas de la inmobiliaria ULP San Luis
    private final LatLng inmobiliaria = new LatLng(-33.1500, -66.3067);

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    inicioViewModel.obtenerUbicacion();
                } else {
                    Toast.makeText(getContext(), "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);
        inicioViewModel = new ViewModelProvider(this).get(InicioViewModel.class);
        tvDistancia = root.findViewById(R.id.tv_distancia);

        // Configurar el mapa
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Observar cambios de ubicación
        inicioViewModel.getMlocation().observe(getViewLifecycleOwner(), location -> {
            if (location != null) {
                actualizarDistancia(location);
            } else {
                tvDistancia.setText("Ubicación no disponible");
            }
        });

        verificarPermisoUbicacion();
        return root;
    }

    private void verificarPermisoUbicacion() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            inicioViewModel.obtenerUbicacion();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(inmobiliaria).title("Inmobiliaria Alumna Pame"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inmobiliaria, 15f));
    }

    private void actualizarDistancia(Location ubicacionActual) {
        // Crear una ubicación "falsa" para la inmobiliaria
        Location locInmobiliaria = new Location("");
        locInmobiliaria.setLatitude(inmobiliaria.latitude);
        locInmobiliaria.setLongitude(inmobiliaria.longitude);

        float distanciaMetros = ubicacionActual.distanceTo(locInmobiliaria);
        float distanciaKm = distanciaMetros / 1000f;

        tvDistancia.setText(String.format("Estás a %.2f km de la inmobiliaria", distanciaKm));
    }
}
