package com.das.daniel.viajeweb.Adaptadores;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.das.daniel.viajeweb.ItemClickListener;
import com.das.daniel.viajeweb.Modelo.Viaje;
import com.das.daniel.viajeweb.R;
import com.das.daniel.viajeweb.ReciboActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel on 16/09/2017.
 */
public class ListaViajeAdapter extends RecyclerView.Adapter<ListaViajeAdapter.MyViewHolder> implements  Validator.ValidationListener{

    private List<Viaje> viajesList;
    private Context context;
    private LayoutInflater inflater;
    Activity mActivity;

    @NotEmpty
    @Pattern(regex ="[a-z A-Z Ñ ñ Á-Ú á-ú Ü ü]{3,50}",message = "Ingrese sólo letras, rango: 3-50")
    private EditText _etNombre;

    @NotEmpty
    @Pattern(regex ="[0123456789]{5,10}",message = "Ingrese sólo números, rango 5-10")
    private EditText _etCi;

    @NotEmpty
    @Pattern(regex ="[a-z A-Z Ñ ñ Á-Ú á-ú Ü ü]{3,50}",message = "Ingrese sólo letras, rango: 3-50")
    private EditText _etApellido;

    private Button _btnInsertarReserva, _btnCancelarReserva;
    private String _codigo, _destino, _horario, _precio;

    Validator validator;

    public ListaViajeAdapter(Context context, List<Viaje> viajesList, Activity mActivity) {
        //parámetros que se envian desde MainActivity
        this.context = context;
        this.viajesList = viajesList;
        this.mActivity=mActivity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = inflater.inflate(R.layout.items_viaje, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Viaje viajes = viajesList.get(position);
        //Pass the values of feeds object to Views
        holder._tvCodigo.setText("Código: "+viajes.getCodigo());
        holder._tvDestino.setText("Destino: "+viajes.getDestino());
        holder._tvHorario.setText("Horario: "+viajes.getHorario());
        holder._tvPrecio.setText("Precio: "+viajes.getPrecio());
        holder._tvFlota.setText("Flota: "+viajes.getFlota());
        Glide.with(context).load(viajes.getImagen()).into(holder._imgFoto);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //Acción al presionar en un item del recycler
                mostrarDialogo(viajes.getCodigo(), viajes.getDestino(), viajes.getHorario(),viajes.getPrecio());

            }
        });

    }

    @Override
    public int getItemCount() {
        return viajesList.size();
    }



    private void mostrarDialogo(String codigo, String destino, String horario, String precio)
    {
        validator = new Validator(this);
        validator.setValidationListener(this);
        final Dialog d = new Dialog(context);
        d.setContentView(R.layout.nueva_reserva);

        _codigo=codigo;
        _destino=destino;
        _horario=horario;
        _precio=precio;

        _etNombre= (EditText) d.findViewById(R.id.etNombre);
        _etApellido= (EditText) d.findViewById(R.id.etApellido);
        _etCi= (EditText) d.findViewById(R.id.etCi);
        _btnInsertarReserva= (Button) d.findViewById(R.id.btnInsertarReserva);
        _btnCancelarReserva= (Button) d.findViewById(R.id.btnCancelarReserva);

        _btnInsertarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
        _btnCancelarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.hide();
            }
        });
        d.show();
    }

    @Override
    public void onValidationSucceeded() {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://192.168.1.5:81/viajes/insertarReserva.php"; // url/ip donde está la base de datos y los archivos php

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"No se pudo realizar la accion", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                //Parámetros para apsar por el método POST
                Map<String, String > params=new HashMap<String, String>();
                params.put("id", "");
                params.put("Nombre", _etNombre.getText()+"");
                params.put("Apellido", _etApellido.getText()+"");
                params.put("Ci", _etCi.getText()+"");
                params.put("FkViaje", _codigo);

                return params;
            }
        };
        //Añadir el Request al RequestQueue
        queue.add(stringRequest);

        //Pasa parámetros a ReciboActivity
        Intent intent=new Intent(context, ReciboActivity.class);
        intent.putExtra("Nombre", _etNombre.getText()+"");
        intent.putExtra("Apellido", _etApellido.getText()+"");
        intent.putExtra("Ci", _etCi.getText()+"");
        intent.putExtra("Destino", _destino);
        intent.putExtra("Horario", _horario);
        intent.putExtra("Precio", _precio);

        context.startActivity(intent);
        Toast.makeText(context, "Reservar guardada correctamente", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(context);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView _tvCodigo, _tvDestino, _tvHorario, _tvPrecio, _tvFlota;
        private ImageView _imgFoto;
        ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            _tvCodigo = (TextView) itemView.findViewById(R.id.tvCodigo);
            _tvDestino = (TextView) itemView.findViewById(R.id.tvDestino);
            _tvHorario = (TextView) itemView.findViewById(R.id.tvHorario);
            _tvPrecio = (TextView) itemView.findViewById(R.id.tvPrecio);
            _tvFlota = (TextView) itemView.findViewById(R.id.tvFlota);
            _imgFoto= (ImageView) itemView.findViewById(R.id.imgFoto);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }
    }
}
