package com.das.daniel.ventalocal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.das.daniel.ventalocal.DB.DBManagerProducto;
import com.das.daniel.ventalocal.DB.DBManagerVenta;
import com.das.daniel.ventalocal.Modelo.Producto;
import com.das.daniel.ventalocal.adaptadores.VentaAdapter;

import java.util.List;

public class VentaActivity extends AppCompatActivity {

    private DBManagerProducto managerProducto;
    private DBManagerVenta managerVenta;
    private RecyclerView recycler;
    private VentaAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    private List<Producto> listaItemsProductos;
    private EditText _etBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        managerProducto= new DBManagerProducto(this);
        _etBuscar= (EditText) findViewById(R.id.etBuscarVenta);

        inicializarRecicler();

        _etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                listaItemsProductos = managerProducto.Buscar(_etBuscar.getText().toString());
                // Crear un nuevo adaptador
                adapter = new VentaAdapter(listaItemsProductos, VentaActivity.this);
                recycler.setAdapter(adapter);
                recycler.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void inicializarRecicler() {
        listaItemsProductos = managerProducto.getProductosList();

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.recicladorVenta);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new VentaAdapter(listaItemsProductos, this);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());
    }

    public  void recargarRecicler() {
        //cargar datos
        listaItemsProductos = managerProducto.getProductosList();
        // Crear un nuevo adaptador
        adapter = new VentaAdapter(listaItemsProductos, this);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.producto:
                Intent intent = new Intent(VentaActivity.this,
                        MainActivity.class);
                startActivity(intent);

                return  true;

            case R.id.venta:
                finish();
                startActivity(getIntent());
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
