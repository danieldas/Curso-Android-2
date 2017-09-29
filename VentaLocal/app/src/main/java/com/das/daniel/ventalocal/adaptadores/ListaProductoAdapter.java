package com.das.daniel.ventalocal.adaptadores;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.das.daniel.ventalocal.DB.DBManagerProducto;
import com.das.daniel.ventalocal.ItemClickListener;
import com.das.daniel.ventalocal.MainActivity;
import com.das.daniel.ventalocal.Modelo.Producto;
import com.das.daniel.ventalocal.R;

import java.util.List;

/**
 * Created by Daniel on 15/09/2017.
 */
public class ListaProductoAdapter extends RecyclerView.Adapter<ListaProductoAdapter.ListaViewHolder> {
    private Context mainContext;

    // declarar componentes: private EditText, Spinner, Button
    Button _btnActualizar, _btnDarDeBaja;
    EditText _etDEscripcion, _etPrecio, _etCantidad;
    Spinner _spTipo;

    int imagen1= R.drawable.ic_celular;
    int imagen2=R.drawable.ic_ropa;

    private List<Producto> items;   // de modelo
    private DBManagerProducto managerProducto;

    public ListaProductoAdapter(List<Producto> items, Context mainContext) {
        this.mainContext = mainContext;
        this.items = items;
    }

    static class ListaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView tvIdProd, tvDescripcion, tvPrecio, tvCantidad;
        ImageView imageview;

        ItemClickListener itemClickListener;

        public ListaViewHolder(View v) {
            super(v);

            this.tvIdProd = (TextView) v.findViewById(R.id.tvNumero);
            this.imageview= (ImageView) v.findViewById(R.id.imgGeneral);
            this.tvDescripcion = (TextView) v.findViewById(R.id.tvDescripcion);
            this.tvPrecio = (TextView) v.findViewById(R.id.tvPrecio);
            this.tvCantidad = (TextView) v.findViewById(R.id.tvCantidad);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)  {
            this.itemClickListener.onItemClick(view,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }
    }
    @Override
    public ListaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.items_recycler, viewGroup, false);

        return new ListaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListaViewHolder viewHolder, int position) {

        Producto item = items.get(position);
        viewHolder.itemView.setTag(item);  //guardar item

        if (item.getTipoProducto().equals("Celular"))
        {
            viewHolder.imageview.setImageResource(imagen1);
        }
        else
        {
            viewHolder.imageview.setImageResource(imagen2);
        }

        viewHolder.tvIdProd.setText("Nº: " + item.get_ID());
        viewHolder.tvDescripcion.setText("Descripción: " + item.getDescripcion());
        viewHolder.tvCantidad.setText("Cantidad: " + item.getCantidad());
        viewHolder.tvPrecio.setText("Precio: " + item.getPrecio());

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, final int pos) {
                //OPEN DETAIL ACTIVITY
                //PASS DATA

                //Instancia de Dialog
                final Dialog d = new Dialog(mainContext);
                d.setContentView(R.layout.detalle_producto);

                _etDEscripcion = (EditText) d.findViewById(R.id.etDescripcionD);
                _etPrecio = (EditText) d.findViewById(R.id.etPrecioD);
                _etCantidad = (EditText) d.findViewById(R.id.etCantidadD);
                _spTipo = (Spinner) d.findViewById(R.id.spTipoD);
                _btnActualizar = (Button) d.findViewById(R.id.btnActualizarProducto);
                _btnDarDeBaja = (Button) d.findViewById(R.id.btnBajaProducto);


                int posicion = 0;
                final String itemCmb=items.get(pos).getTipoProducto();
                if (itemCmb.equals("Celular"))
                {
                    posicion=1;
                }

                //textoIdProd.setText(items.get(pos).getIdProd());
                _etDEscripcion.setText(items.get(pos).getDescripcion());
                _etCantidad.setText(items.get(pos).getCantidad());
                _etPrecio.setText(items.get(pos).getPrecio());

                _spTipo.setSelection(posicion);

                //instanciar de DBManager Producto
                managerProducto= new DBManagerProducto(mainContext);


                _btnActualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        managerProducto.actualizarProducto(items.get(pos).get_ID(),
                                _etDEscripcion.getText().toString(),
                                _etCantidad.getText().toString(),
                                _etPrecio.getText().toString(),
                                _spTipo.getSelectedItem().toString());

                        ((MainActivity) mainContext).recargarRecicler();
                        d.hide();
                    }
                });

                _btnDarDeBaja.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(mainContext)
                                .setTitle("Dar de baja a producto")
                                .setMessage("¿Estás segura en dar de baja a este producto?")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        managerProducto.darBajaProducto(items.get(pos).get_ID(), "Baja");

                                        ((MainActivity) mainContext).recargarRecicler();
                                        d.hide();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setIcon(android.R.drawable.ic_menu_delete)
                                .show();
                    }
                });

                d.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}