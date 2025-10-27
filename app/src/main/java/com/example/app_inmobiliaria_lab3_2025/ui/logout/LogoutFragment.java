package com.example.app_inmobiliaria_lab3_2025.ui.logout;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.app_inmobiliaria_lab3_2025.R;
import com.example.app_inmobiliaria_lab3_2025.databinding.FragmentLogoutBinding;
import com.example.app_inmobiliaria_lab3_2025.ui.MainActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        logout();
        View root = binding.getRoot();
        return root;
    }


    private void logout() {
        String encabezado = "<font color='#6200EE'><b><big>Cerrar sesión</big></b></font>" ;
        String cuerpo = "<font color='#000000'><big>¿Deseás salir de la aplicación?</big></font>" ;

        androidx.appcompat.app.AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(Html.fromHtml(encabezado))
                .setMessage(Html.fromHtml(cuerpo))
                .setPositiveButton("Aceptar", (dialogInterface, i) -> {
                    System.exit(0);
                })
                .setNegativeButton("Cancelar", (dialogInterface, i) -> {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                })
                .setBackground(getResources().getDrawable(R.drawable.dialog_background, null))
                .show();

        // Colores de los botones
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#6200EE"));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }

}
