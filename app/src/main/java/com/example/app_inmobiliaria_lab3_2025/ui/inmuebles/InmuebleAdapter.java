package com.example.app_inmobiliaria_lab3_2025.ui.inmuebles;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_inmobiliaria_lab3_2025.R;
import com.example.app_inmobiliaria_lab3_2025.modelo.Inmuebles;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.InmuebleViewHolder> {

    private List<Inmuebles> lista;
    private Context context;
    private String baseUrl = "http://192.168.1.13:5000"; // para dispositivo físico
    // String baseUrl = "http://10.0.2.2:5000"; // para dispositivo virtual


    public InmuebleAdapter(Context context, List<Inmuebles> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_inmueble, parent, false);
        return new InmuebleViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleViewHolder holder, int position) {
        Inmuebles inmueble = lista.get(position);

        holder.TvDireccion.setText(inmueble.getDireccion());
        holder.TvAmbientes.setText("Ambientes: " + inmueble.getAmbientes());
        holder.TvPrecio.setText("Precio: $" + inmueble.getPrecio());
        holder.TvEstado.setText("Estado: " + (inmueble.getEstado()== 1 ? "Disponible" : "No disponible"));

        holder.BtnEditar.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", inmueble);
            Navigation.findNavController(v).navigate(R.id.detalleInmuebleFragment, bundle);
        });

        Glide.with(context)
                .load(baseUrl + inmueble.getImageUrl())
                //.load("http://localhost:5000" + inmueble.getImageUrl()) // Ajustá si usás IP o dominio
                .placeholder(R.drawable.ic_home)
                .into(holder.IvImagenInmueble);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class InmuebleViewHolder extends RecyclerView.ViewHolder {
        private TextView TvDireccion, TvAmbientes, TvPrecio, TvEstado;
        private ImageButton BtnEditar;
        private ImageView IvImagenInmueble;

        public InmuebleViewHolder(@NonNull View itemView) {
            super(itemView);
            TvDireccion = itemView.findViewById(R.id.textDireccion);
            TvAmbientes = itemView.findViewById(R.id.textAmbientes);
            TvPrecio = itemView.findViewById(R.id.textPrecio);
            IvImagenInmueble = itemView.findViewById(R.id.imageInmueble);
            TvEstado = itemView.findViewById(R.id.textEstado);
            BtnEditar = itemView.findViewById(R.id.btnEditar);

        }
    }
}

