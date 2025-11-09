package com.example.app_inmobiliaria_lab3_2025.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_inmobiliaria_lab3_2025.R;
import com.example.app_inmobiliaria_lab3_2025.databinding.FragmentInmueblesBinding;
import com.example.app_inmobiliaria_lab3_2025.modelo.Inmuebles;

import java.util.ArrayList;
import java.util.List;


public class InmueblesFragment extends Fragment {

    private FragmentInmueblesBinding binding;
    private InmuebleAdapter adapter;
    private List<Inmuebles> listaInmuebles = new ArrayList<>();
    private InmuebleViewModel viewModel;

    public InmueblesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);

        binding.fabCrearInmueble.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.crearInmuebleFragment);
        });

        binding.recyclerInmuebles.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new InmuebleAdapter(getContext(), listaInmuebles);
        binding.recyclerInmuebles.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(InmuebleViewModel.class);
        viewModel.getInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            listaInmuebles.clear();
            listaInmuebles.addAll(inmuebles);
            adapter.notifyDataSetChanged();
        });

        viewModel.cargarInmuebles();

        return binding.getRoot();
    }
}
