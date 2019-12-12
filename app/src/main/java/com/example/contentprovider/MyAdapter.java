package com.example.contentprovider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.ItemHolder>{

    private LayoutInflater inflater;
    private List<Contacto> contactosList = new ArrayList<>();
    private Context miContexto;
    private String direccion;

    public MyAdapter(Context context) {
        inflater= LayoutInflater.from(context);
        miContexto = context;
    }

    @NonNull
    @Override
    public MyAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= inflater.inflate(R.layout.item_contacto,parent,false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.ItemHolder holder, int position) {
        final Contacto contacto = contactosList.get(position);

        if(contacto.getEmail().equalsIgnoreCase("null")){
            holder.cl.setClickable(false);
            holder.cl.setBackgroundColor(miContexto.getResources().getColor(R.color.noDisponible));
            holder.tvNombre.setText(contacto.getNombre());
            holder.tvDireccion.setText("No hay email");
            holder.tvTelefono.setText(""+contacto.getNumero());
        }else{
            if (contacto != null && !contacto.getNombre().equalsIgnoreCase("") &&
                    !contacto.getNumero().equalsIgnoreCase("") &&
                    !contacto.getEmail().equalsIgnoreCase("")){
                holder.tvNombre.setText(contacto.getNombre());
                holder.tvDireccion.setText(contacto.getEmail());
                holder.tvTelefono.setText(""+contacto.getNumero());
            }

            holder.cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    direccion = contacto.getEmail();
                    mandarCorreo(direccion);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        int elements=0;
        if(contactosList !=null){
            elements= contactosList.size();
        }
        return elements;
    }

    public void setData(List<Contacto> contactoList){
        SharedPreferences sharedPreferences = miContexto.getSharedPreferences(MainActivity.TAG, Context.MODE_PRIVATE);
        String nombre = sharedPreferences.getString(MainActivity.NOMBRE, "");
        nombre = nombre.toUpperCase();
        String numero = sharedPreferences.getString(MainActivity.NUMERO, "");
        for (int i = 0; i < contactoList.size(); i++) {
            if(contactoList.get(i).getNombre().toUpperCase().contains(nombre) || contactoList.get(i).getNumero().contains(numero)){
                this.contactosList.add(contactoList.get(i));
            }
        }
        notifyDataSetChanged();
    }

    public List<Contacto> getData(){
        return this.contactosList;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre, tvTelefono, tvDireccion;
        private ConstraintLayout  cl;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre=itemView.findViewById(R.id.tvNombre);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            cl = itemView.findViewById(R.id.cl);
        }
    }

    private void mandarCorreo(String direccion) {
        String[] TO = {direccion};
        String[] CC = {""};

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto: "));

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PRUEBA APP ENVIAR CORREO");

        String title = "Mandar este mail con...";

        Intent chooser = Intent.createChooser(emailIntent, title);
        if (emailIntent.resolveActivity(miContexto.getPackageManager()) != null){
            miContexto.startActivity(chooser);
        }
    }
}
