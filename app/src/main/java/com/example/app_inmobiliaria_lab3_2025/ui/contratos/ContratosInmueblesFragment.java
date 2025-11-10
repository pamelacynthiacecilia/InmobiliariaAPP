package com.example.app_inmobiliaria_lab3_2025.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_inmobiliaria_lab3_2025.databinding.FragmentContratosInmuebleBinding;
import com.example.app_inmobiliaria_lab3_2025.modelo.Contratos;
import com.example.app_inmobiliaria_lab3_2025.modelo.Inmuebles;

import java.util.ArrayList;
import java.util.List;

public class ContratosInmueblesFragment extends Fragment {

    private FragmentContratosInmuebleBinding binding;
    private ContratosInmuebleViewModel viewModel;
    private ContratosAdapter adapter;
    private List<Contratos> listaContratos = new ArrayList<>();

    public ContratosInmueblesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContratosInmuebleBinding.inflate(inflater, container, false);
        binding.recyclerContratosInmueble.setLayoutManager(new LinearLayoutManager(getContext()));

        Inmuebles inmueble = (Inmuebles) getArguments().getSerializable("inmueble");
        if (inmueble != null) {
            adapter = new ContratosAdapter(getContext(), listaContratos, inmueble);
            binding.recyclerContratosInmueble.setAdapter(adapter);

            viewModel = new ViewModelProvider(this).get(ContratosInmuebleViewModel.class);
            viewModel.cargarContratos(inmueble.getId());

            viewModel.getContratos().observe(getViewLifecycleOwner(), contratos -> {
                listaContratos.clear();
                listaContratos.addAll(contratos);
                adapter.notifyDataSetChanged();
            });
        }

        return binding.getRoot();
    }
}