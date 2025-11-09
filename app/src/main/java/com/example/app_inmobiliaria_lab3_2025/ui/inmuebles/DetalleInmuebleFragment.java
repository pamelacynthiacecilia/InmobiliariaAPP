package com.example.app_inmobiliaria_lab3_2025.ui.inmuebles;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.app_inmobiliaria_lab3_2025.R;
import com.example.app_inmobiliaria_lab3_2025.databinding.FragmentDetalleInmuebleBinding;
import com.example.app_inmobiliaria_lab3_2025.modelo.Inmuebles;

public class DetalleInmuebleFragment extends Fragment {

    private FragmentDetalleInmuebleBinding binding;
    private DetalleInmuebleViewModel viewModel;
    private Inmuebles inmueble;
    private String baseUrl = "http://192.168.1.13:5000"; // para dispositivo físico
   // String baseUrl = "http://10.0.2.2:5000"; // para dispositivo virtual


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);

        if (getArguments() != null) {
            inmueble = (Inmuebles) getArguments().getSerializable("inmueble");
            cargarDatos(inmueble);
        }

        viewModel.getResultadoExito().observe(getViewLifecycleOwner(), exito -> {
            if (exito != null && exito) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Éxito")
                        .setMessage("El estado del inmueble fue actualizado correctamente.")
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                            // Volver al fragment de lista
                            Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_inmuebles);
                        })
                        .show();
            }
        });

        viewModel.getResultadoError().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Error al guardar inmueble")
                        .setMessage(errorMsg)
                        .setPositiveButton("Aceptar", null)
                        .show();
            }
        });


        binding.btnCambiarEstado.setOnClickListener(v -> mostrarConfirmacion());
    }

    private void cargarDatos(Inmuebles i) {
        binding.textTipo.setText("Tipo: " + i.getTipo());
        binding.textUso.setText("Uso: " + i.getUso());
        binding.textDireccion.setText("Dirección: " + i.getDireccion());
        binding.textAmbientes.setText("Ambientes: " + i.getAmbientes());
        binding.textPrecio.setText("Precio: $" + i.getPrecio());
        binding.textEstado.setText("Estado: " + (i.getEstado() == 1 ? "Disponible" : "No disponible"));

        String imageUrl = i.getImageUrl();
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
        Glide.with(requireContext())
                .load(baseUrl + i.getImageUrl().replace("\\", "/"))
                .placeholder(R.drawable.ic_home)
                .into(binding.imageDetalle);
        } else {
            binding.imageDetalle.setImageResource(R.drawable.ic_home); // imagen por defecto
        }
    }

    private void mostrarConfirmacion() {
        Inmuebles inmuebleParaModEstado =new Inmuebles();

        String mensaje = inmueble.getEstado() == 1
                ? "¿Querés marcar este inmueble como NO disponible?"
                : "¿Querés marcar este inmueble como DISPONIBLE?";

        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar cambio de estado")
                .setMessage(mensaje)
                .setPositiveButton("Sí", (dialog, which) -> {
                    int nuevoEstado = inmueble.getEstado() == 1 ? 2 : 1;
                    inmueble.setEstado(nuevoEstado);
                    inmuebleParaModEstado.setEstado(nuevoEstado);
                    inmuebleParaModEstado.setId(inmueble.getId());
                    inmuebleParaModEstado.setPrecio(inmueble.getPrecio());
                    inmuebleParaModEstado.setAmbientes(inmueble.getAmbientes());

                    cargarDatos(inmueble); // refrescar vista
                    viewModel.actualizarEstado(inmueble.getId(), inmuebleParaModEstado);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
