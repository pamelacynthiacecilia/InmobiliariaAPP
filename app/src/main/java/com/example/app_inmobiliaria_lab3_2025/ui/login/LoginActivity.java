package com.example.app_inmobiliaria_lab3_2025.ui.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_inmobiliaria_lab3_2025.R;
import com.example.app_inmobiliaria_lab3_2025.databinding.ActivityLoginBinding;
import com.example.app_inmobiliaria_lab3_2025.request.ApiClient;
import com.example.app_inmobiliaria_lab3_2025.ui.MainActivity;


public class LoginActivity extends AppCompatActivity {
    private EditText etUsuario;
    private EditText etClave;
    private Button btLogin;
    private String correoIngresado;



    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //infla la vista asociada a la activity
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //inicializa observer a los mutables
        inicializar();
    }

    private void inicializar() {

        //seteo de variables con valor de componentes activity
        etUsuario = findViewById(R.id.etUsuario);
        etClave = findViewById(R.id.etClave);
        btLogin = findViewById(R.id.btLogin);
       // Log.d("Salida", btLogin.toString());
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correoIngresado = etUsuario.getText().toString();

                logueo(view);

            }
        });


        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Error")
                        .setMessage(error)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss())
                        .show();

            }
        });

        viewModel.getLoginExitoso().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean exitoso) {
                if (exitoso) {
                    ApiClient.guardarEmail(LoginActivity.this,correoIngresado);
                    //Toast.makeText(LoginActivity.this, "Login exitoso "+ etUsuario.getText().toString(), Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Inicio de sesión exitoso")
                            .setMessage("Bienvenido, " + etUsuario.getText().toString() + " 👋")
                            .setPositiveButton("Aceptar", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();


                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            }
        });


    }

    public void logueo(View view) {

        String usuario = etUsuario.getText().toString();
        String clave = etClave.getText().toString();
        viewModel.login(usuario, clave);




    }

}