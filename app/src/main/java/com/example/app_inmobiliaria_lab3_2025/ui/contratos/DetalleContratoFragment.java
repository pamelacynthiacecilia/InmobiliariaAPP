package com.example.app_inmobiliaria_lab3_2025.ui.contratos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_inmobiliaria_lab3_2025.R;
import com.example.app_inmobiliaria_lab3_2025.databinding.FragmentDetalleContratoBinding;
import com.example.app_inmobiliaria_lab3_2025.modelo.Contratos;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetalleContratoFragment extends Fragment {

    private FragmentDetalleContratoBinding binding;
    private DetalleContratoViewModel viewModel;

    public DetalleContratoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        if (getArguments() != null) {
            Contratos contrato = (Contratos) getArguments().getSerializable("contrato");
            viewModel.setContrato(contrato);
        }

        viewModel.getContrato().observe(getViewLifecycleOwner(), contrato -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            binding.tvIdContrato.setText("Contrato #" + contrato.getId());
            binding.tvFechas.setText("Inicio: " + sdf.format(contrato.getFechaInicio()) + " - Fin: " + sdf.format(contrato.getFechaFin()));
            binding.tvPrecio.setText("Precio: $" + contrato.getPrecio());

            if (contrato.getInquilinoContrato() != null) {
                binding.tvInquilino.setText("Inquilino: " + contrato.getInquilinoContrato().getNombreCompleto());

                String telefono = contrato.getInquilinoContrato().getTel();
                binding.tvContacto.setText("Contacto: " + (telefono != null ? telefono : "No disponible"));
            }else {
                binding.tvInquilino.setText("Inquilino no disponible");
                binding.tvContacto.setText("");
            }
        });

        return binding.getRoot();
    }
}