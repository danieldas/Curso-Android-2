package com.das.daniel.ventalocal;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.das.daniel.ventalocal.DB.DBManagerProducto;
import com.das.daniel.ventalocal.Modelo.Producto;
import com.das.daniel.ventalocal.adaptadores.ListaProductoAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBManagerProducto managerProducto;
    private RecyclerView recycler;
    private ListaProductoAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    private List<Producto> listaItemsProductos;

    private EditText _etDescripcion, _etCantidad, _etPrecio, _etBuscar;
    private Spinner _spTipo;
    private Button _btnInsertar, _btnCancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        managerProducto= new DBManagerProducto(this);
        _etBuscar= (EditText) findViewById(R.id.etBuscar);

        inicializarRecicler();


        FloatingActionButton btnDialogoInsertar = (FloatingActionButton) findViewById(R.id.btnInsertarProducto);
        btnDialogoInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogo();
            }
        });
        _etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                listaItemsProductos = managerProducto.Buscar(_etBuscar.getText().toString());
                // Crear un nuevo adaptador
                adapter = new ListaProductoAdapter(listaItemsProductos, MainActivity.this);
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
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new ListaProductoAdapter(listaItemsProductos, this);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());
    }

    public  void recargarRecicler() {
        //cargar datos
        listaItemsProductos = managerProducto.getProductosList();
        // Crear un nuevo adaptador
        adapter = new ListaProductoAdapter(listaItemsProductos, this);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());
    }


    private void mostrarDialogo() {
        final Dialog d=new Dialog(this);
        d.setContentView(R.layout.nuevo_producto);
        _etDescripcion= (EditText) d.findViewById(R.id.etDescripcion);
        _etCantidad= (EditText) d.findViewById(R.id.etCantidad);
        _etPrecio= (EditText) d.findViewById(R.id.etPrecio);
        _spTipo= (Spinner) d.findViewById(R.id.spTipo);

        _btnInsertar= (Button) d.findViewById(R.id.btnInsertarProducto);
        _btnCancelar= (Button) d.findViewById(R.id.btnCancelarProducto);

        _btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //INSERTAR
                managerProducto.insertarProducto(null, _etDescripcion.getText()+"", _etCantidad.getText()+"",
                        _etPrecio.getText()+"", "Alta", _spTipo.getSelectedItem()+""

                    );
                _etDescripcion.setText("");
                _etPrecio.setText("");
                _etCantidad.setText("");
                recargarRecicler();
            }
        });

        _btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();

            }
        });
        d.show();
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
                finish();
                startActivity(getIntent());
                return  true;

            case R.id.venta:
                Intent intent = new Intent(MainActivity.this,
                        VentaActivity.class);
                startActivity(intent);
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}