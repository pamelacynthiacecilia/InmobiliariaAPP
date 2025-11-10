package com.example.app_inmobiliaria_lab3_2025.ui.inmuebles;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.app_inmobiliaria_lab3_2025.R;
import com.example.app_inmobiliaria_lab3_2025.databinding.FragmentCrearInmuebleBinding;

import java.io.IOException;

public class CrearInmuebleFragment extends Fragment {

    private FragmentCrearInmuebleBinding binding;
    private CrearInmuebleViewModel viewModel;
    private Uri imagenUri;

    private final ActivityResultLauncher<Intent> seleccionarImagenLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imagenUri = result.getData().getData();
                    binding.imagePreview.setVisibility(View.VISIBLE);
                    Glide.with(requireContext()).load(imagenUri).into(binding.imagePreview);
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrearInmuebleBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CrearInmuebleViewModel.class);

        viewModel.getResultadoExito().observe(getViewLifecycleOwner(), exito -> {
            if (exito != null && exito) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Éxito")
                        .setMessage("El inmueble fue creado con exito.")
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


        binding.btnSeleccionarImagen.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            seleccionarImagenLauncher.launch(intent);
        });

        binding.btnGuardarInmueble.setOnClickListener(v -> {
            try {
                guardarInmueble();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return binding.getRoot();
    }

    private void guardarInmueble() throws IOException {
        String tipo = binding.editTipo.getText().toString().trim();
        String uso = binding.editUso.getText().toString().trim();
        String direccion = binding.editDireccion.getText().toString().trim();
        String ambientesStr = binding.editAmbientes.getText().toString().trim();
        String precioStr = binding.editPrecio.getText().toString().trim();

        if (TextUtils.isEmpty(tipo) || TextUtils.isEmpty(uso) || TextUtils.isEmpty(direccion)
                || TextUtils.isEmpty(ambientesStr) || TextUtils.isEmpty(precioStr)) {
            Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        int ambientes;
        double precio;
        try {
            ambientes = Integer.parseInt(ambientesStr);
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Ambientes y precio deben ser numéricos", Toast.LENGTH_SHORT).show();
            return;
        }

        int estado = binding.radioEstado.getCheckedRadioButtonId() == binding.radioDisponible.getId() ? 1 : 0;

        viewModel.crearInmueble(tipo, uso, direccion, String.valueOf(ambientes), String.valueOf(precio), estado, imagenUri);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
