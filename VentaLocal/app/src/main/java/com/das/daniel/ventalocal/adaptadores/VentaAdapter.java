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
import android.widget.Toast;

import com.das.daniel.ventalocal.DB.DBManagerProducto;
import com.das.daniel.ventalocal.DB.DBManagerVenta;
import com.das.daniel.ventalocal.ItemClickListener;
import com.das.daniel.ventalocal.MainActivity;
import com.das.daniel.ventalocal.Modelo.Producto;
import com.das.daniel.ventalocal.R;
import com.das.daniel.ventalocal.VentaActivity;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Daniel on 28/09/2017.
 */
public class VentaAdapter extends RecyclerView.Adapter<VentaAdapter.ListaViewHolder> {
    private Context mainContext;

    // declarar componentes: private EditText, Spinner, Button
    Button _btnInsertarV, _btnCancelarV;
    EditText _etCantidad, _etObservacion;
    Spinner _spTipo;

    int imagen1= R.drawable.ic_celular;
    int imagen2=R.drawable.ic_ropa;

    private List<Producto> items;   // de modelo
    private DBManagerProducto managerProducto;
    private DBManagerVenta managerVenta;

    public VentaAdapter(List<Producto> items, Context mainContext) {
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

                final Dialog d = new Dialog(mainContext);
                d.setContentView(R.layout.nueva_venta);

                _etObservacion = (EditText) d.findViewById(R.id.etObservacion);
                _etCantidad = (EditText) d.findViewById(R.id.etCantidadVenta);

                _btnInsertarV = (Button) d.findViewById(R.id.btnInsertarVenta);
                _btnCancelarV = (Button) d.findViewById(R.id.btnCancelarVenta);

                managerProducto= new DBManagerProducto(mainContext);
                managerVenta= new DBManagerVenta(mainContext);

                _btnInsertarV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar Cal= Calendar.getInstance();
                        String fechaActual= Cal.get(Cal.DATE)+"/"+(Cal.get(Cal.MONTH)+1)+"/"+Cal.get(Cal.YEAR)+" "+Cal.get(Cal.HOUR_OF_DAY)+":"+Cal.get(Cal.MINUTE)+":"+Cal.get(Cal.SECOND);


                        managerVenta.insertarVenta(null, _etObservacion.getText()+"", _etCantidad.getText()+"",
                             fechaActual,  items.get(pos).get_ID());
                        int nuevaCantidad = Integer.parseInt(items.get(pos).getCantidad()) - Integer.parseInt(_etCantidad.getText()+"");
                        managerProducto.actualizarCantidad(items.get(pos).get_ID(), nuevaCantidad + "");
                        ((VentaActivity) mainContext).recargarRecicler();
                        d.hide();
                        Toast.makeText(mainContext,"Venta registrada correctamente", Toast.LENGTH_LONG).show();
                    }
                });

                _btnCancelarV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();

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
