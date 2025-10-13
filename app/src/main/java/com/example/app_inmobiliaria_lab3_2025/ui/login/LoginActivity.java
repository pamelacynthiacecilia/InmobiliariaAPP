package com.example.app_inmobiliaria_lab3_2025.ui.login;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_inmobiliaria_lab3_2025.R;
import com.example.app_inmobiliaria_lab3_2025.databinding.ActivityLoginBinding;
import com.example.app_inmobiliaria_lab3_2025.MainActivity;


public class LoginActivity extends AppCompatActivity {
    private EditText etUsuario;
    private EditText etClave;
    private Button btLogin;


    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_login);
        inicializar();
    }

    private void inicializar() {
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getLoginExitoso().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean exitoso) {
                if (exitoso) {
                    Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        etUsuario = findViewById(R.id.etUsuario);
        etClave = findViewById(R.id.etClave);
        btLogin = findViewById(R.id.btLogin);
        Log.d("Salida", btLogin.toString());
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logueo(view);

            }
        });
    }

    public void logueo(View view) {

        String usuario = etUsuario.getText().toString();
        String clave = etClave.getText().toString();
        viewModel.login(usuario, clave);

    }

}