package com.das.daniel.viajeweb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.das.daniel.viajeweb.Adaptadores.ListaViajeAdapter;
import com.das.daniel.viajeweb.Modelo.Viaje;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    String url = "http://192.168.1.5:81/viajes/viajeListado.php"; // url/ip donde está la base de datos y los archivos php
    RecyclerView recyclerView;
    List<Viaje> viajesList = new ArrayList<Viaje>();
    ListaViajeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.reciclador);
        adapter = new ListaViajeAdapter(this, viajesList, MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
        //Obteniendo la instancia de Volley Request Queue
        queue = NetworkController.getInstance(this).getRequestQueue();

        cargarRecycler();
    }

    private void cargarRecycler()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i= 0; i < response.length(); i++)
                {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Viaje viaje = new Viaje(object.getString("Codigo"),
                                object.getString("Destino"), object.getString("Horario"),
                                object.getString("Precio"), object.getString("Flota"),
                                object.getString("Imagen"));

                        viajesList.add(viaje);
                    }
                    catch (Exception ex) {
                        System.out.print(ex.getMessage());
                    }
                    finally {
                        //Notifica al adaptador sobre el cambio
                        adapter.notifyItemChanged(i);
                    }
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print(error.getMessage());
                Toast.makeText(MainActivity.this, "Verifique la salida a internet", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonArrayRequest);
    }
}
