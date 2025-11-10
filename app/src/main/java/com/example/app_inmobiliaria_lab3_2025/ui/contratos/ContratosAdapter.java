package com.example.app_inmobiliaria_lab3_2025.ui.contratos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_inmobiliaria_lab3_2025.R;
import com.example.app_inmobiliaria_lab3_2025.modelo.Contratos;
import com.example.app_inmobiliaria_lab3_2025.modelo.Inmuebles;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ContratosAdapter extends RecyclerView.Adapter<ContratosAdapter.ContratoViewHolder> {

    private List<Contratos> lista;
    private Context context;
    private Inmuebles inmueble; // para mostrar dirección

    public ContratosAdapter(Context context, List<Contratos> lista, Inmuebles inmueble) {
        this.context = context;
        this.lista = lista;
        this.inmueble = inmueble;
    }

    @NonNull
    @Override
    public ContratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_contrato, parent, false);
        return new ContratoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoViewHolder holder, int position) {
        Contratos contrato = lista.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date inicio = contrato.getFechaInicio();
        Date fin = contrato.getFechaFin();


        holder.tvIdContrato.setText("Contrato #" + contrato.getId());

        if (inicio != null && fin != null) {
            holder.tvFechas.setText("Inicio: " + sdf.format(inicio) + " - Fin: " + sdf.format(fin));
        } else {
            holder.tvFechas.setText("Fechas no disponibles");
        }


        holder.tvDireccionInmueble.setText("Dirección: " + inmueble.getDireccion());

        holder.btnVerContrato.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("contrato", contrato);
            bundle.putSerializable("inmueble", inmueble);
            Navigation.findNavController(v).navigate(R.id.detalleContratoFragment, bundle);

        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ContratoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIdContrato, tvFechas, tvDireccionInmueble;
        private ImageButton btnVerContrato;

        public ContratoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdContrato = itemView.findViewById(R.id.tvIdContrato);
            tvFechas = itemView.findViewById(R.id.tvFechas);
            tvDireccionInmueble = itemView.findViewById(R.id.tvDireccionInmueble);
            btnVerContrato = itemView.findViewById(R.id.btnVerContrato);
        }
    }
}
