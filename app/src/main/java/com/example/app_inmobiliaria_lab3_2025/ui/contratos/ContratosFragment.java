package com.example.app_inmobiliaria_lab3_2025.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_inmobiliaria_lab3_2025.databinding.FragmentContratosBinding;
import com.example.app_inmobiliaria_lab3_2025.modelo.Inmuebles;

import java.util.ArrayList;
import java.util.List;

public class ContratosFragment extends Fragment {

    private FragmentContratosBinding binding;
    private ContratosViewModel viewModel;
    private InmuebleContratoAdapter adapter;
    private List<Inmuebles> listaInmuebles = new ArrayList<>();

    public ContratosFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContratosBinding.inflate(inflater, container, false);

        binding.recyclerContratos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new InmuebleContratoAdapter(getContext(), listaInmuebles);
        binding.recyclerContratos.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ContratosViewModel.class);
        viewModel.getInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            listaInmuebles.clear();
            listaInmuebles.addAll(inmuebles);
            adapter.notifyDataSetChanged();
        });

        viewModel.cargarInmuebles();

        return binding.getRoot();
    }
}
