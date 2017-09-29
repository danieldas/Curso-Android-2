package com.das.daniel.viajeweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReciboActivity extends AppCompatActivity {

    TextView _tvDestino, _tvHorario, _tvPrecio, _tvNombre, _tvApellido, _tvCi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibo);

        Intent intent=getIntent();
        String pDestino=intent.getExtras().getString("Destino");
        String pHorario=intent.getExtras().getString("Horario");
        String pPrecio=intent.getExtras().getString("Precio");
        String pNombre=intent.getExtras().getString("Nombre");
        String pApellido=intent.getExtras().getString("Apellido");
        String pCi=intent.getExtras().getString("Ci");

        _tvDestino= (TextView) findViewById(R.id.tvReDestino);
        _tvHorario= (TextView) findViewById(R.id.tvReHorario);
        _tvPrecio= (TextView) findViewById(R.id.tvRePrecio);
        _tvNombre= (TextView) findViewById(R.id.tvReNombre);
        _tvApellido= (TextView) findViewById(R.id.tvReApellido);
        _tvCi= (TextView) findViewById(R.id.tvReCi);

        _tvCi.setText("Ci: "+pCi);
        _tvHorario.setText("Horario : "+pHorario);
        _tvApellido.setText("Apellido: "+pApellido);
        _tvNombre.setText("Nombre: "+pNombre);
        _tvPrecio.setText("Precio: "+pPrecio);
        _tvDestino.setText("Destino: "+pDestino);
    }
}
