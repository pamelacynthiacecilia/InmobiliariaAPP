package com.example.app_inmobiliaria_lab3_2025.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_inmobiliaria_lab3_2025.R;
import com.example.app_inmobiliaria_lab3_2025.modelo.Propietarios;
import com.example.app_inmobiliaria_lab3_2025.request.ApiClient;
import com.example.app_inmobiliaria_lab3_2025.ui.login.LoginViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment {

    private PerfilViewModel perfilViewModel;
    private EditText etId, etDNI, etNombre, etApellido, etEmail, etPass, etTelefono;
    private Button btEditar, btGuardar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        inicializar(root);

        //  Observer del propietario
        perfilViewModel.getPropietarioMutable().observe(getViewLifecycleOwner(), propietario -> {
            if (propietario != null) {
                etId.setText(String.valueOf(propietario.getId()));
                etDNI.setText(propietario.getDni());
                etNombre.setText(propietario.getNombre());
                etApellido.setText(propietario.getApellido());
                etEmail.setText(propietario.getEmail());
                etPass.setText(propietario.getClave());
                etTelefono.setText(propietario.getTel());
            }
        });

        // Observer de mensajes
        perfilViewModel.getMensajeMutable().observe(getViewLifecycleOwner(), mensaje -> {
            if (mensaje != null && !mensaje.isEmpty()) {
                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show();
            }
        });

        // El ViewModel maneja el token internamente
        perfilViewModel.obtenerUsuarioActual();

        return root;
    }

    private void inicializar(View vistaPerfil) {
        etId = vistaPerfil.findViewById(R.id.etId);
        etDNI = vistaPerfil.findViewById(R.id.etDNI);
        etNombre = vistaPerfil.findViewById(R.id.etNombre);
        etApellido = vistaPerfil.findViewById(R.id.etApellido);
        etEmail = vistaPerfil.findViewById(R.id.etEmail);
        etTelefono = vistaPerfil.findViewById(R.id.etTelefono);
        etPass = vistaPerfil.findViewById(R.id.etPass);
        btEditar = vistaPerfil.findViewById(R.id.btEditar);
        btGuardar = vistaPerfil.findViewById(R.id.btGuardar);

        btEditar.setOnClickListener(v -> {

            btEditar.setVisibility(View.INVISIBLE);
            btGuardar.setVisibility(View.VISIBLE);
            habilitarEditex();
        });

        btGuardar.setOnClickListener(v -> {
            Propietarios propietario = new Propietarios(
                    etDNI.getText().toString(),
                    etNombre.getText().toString(),
                    etApellido.getText().toString(),
                    etEmail.getText().toString(),
                    etPass.getText().toString(),
                    etTelefono.getText().toString()
            );

            perfilViewModel.editarPropietario(propietario);

            btEditar.setVisibility(View.VISIBLE);
            btGuardar.setVisibility(View.INVISIBLE);

            DeshabilitarEditex();
        });
    }

    private void habilitarEditex() {
        etDNI.setEnabled(true);
        etNombre.setEnabled(true);
        etApellido.setEnabled(true);
        etEmail.setEnabled(true);
        etPass.setEnabled(true);
        etTelefono.setEnabled(true);
    }

    private void DeshabilitarEditex() {
        etDNI.setEnabled(false);
        etNombre.setEnabled(false);
        etApellido.setEnabled(false);
        etEmail.setEnabled(false);
        etPass.setEnabled(false);
        etTelefono.setEnabled(false);
    }


}
