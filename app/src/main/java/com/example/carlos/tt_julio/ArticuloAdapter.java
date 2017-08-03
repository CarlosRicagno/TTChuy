package com.example.carlos.tt_julio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Carlos on 07/02/2017.
 */

public class ArticuloAdapter
        extends RecyclerView.Adapter<ArticuloAdapter.ArticuloHolder>
         {


    Context contex;
    int layoutResourceId;
    private ArrayList<articulo> articulosLista;
    //private View.OnClickListener listener;

    public ArticuloAdapter(Context context, ArrayList<articulo> arreglo){

        //super(context,R.layout.fila);
        //this.layoutResourceId = layoutResourceId;
        this.contex = context;
        this.articulosLista = arreglo;
        //this.datos = data;
    }

    public static class ArticuloHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        //2
        TextView nombreArticulo;
        TextView numeroArticulos;
        TextView precioUnit;
        TextView precioTotal;
        TextView codigoBarras;

        //3
        private static final String PHOTO_KEY = "PHOTO";

        //4
        public ArticuloHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            nombreArticulo  = (TextView) v.findViewById(R.id.nombreArticulo);
            numeroArticulos = (TextView) v.findViewById(R.id.numeroArticulos);
            precioUnit      = (TextView) v.findViewById(R.id.precioUnitario);
            precioTotal     = (TextView) v.findViewById(R.id.precioTotal);
            codigoBarras    = (TextView) v.findViewById(R.id.numeroCodigo);

           // v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){

        }

    }


    @Override
    public void onBindViewHolder(ArticuloHolder holder, int position) {
        articulo articuloI= articulosLista.get(position);
        holder.nombreArticulo.setText(articuloI.getNombreArticulo());
        holder.numeroArticulos.setText(String.valueOf(articuloI.getNumeroArticulos()));
        holder.precioUnit.setText(String.valueOf(articuloI.getPrecioUnit()));
        holder.precioTotal.setText(String.valueOf(articuloI.getPrecioTotal()));
        holder.codigoBarras.setText(String.valueOf(articuloI.getCodigoBarras()));
    }
    @Override
    public ArticuloHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fila, parent, false);
        return new ArticuloHolder(inflatedView);
    }



    public void updateList(){
        notifyDataSetChanged();
    }

    public float cuentaTotal(){
        int i = this.getItemCount();
        float total = 0;
        for(int j =0;j<i;j++){
           total = articulosLista.get(j).getPrecioUnit()*articulosLista.get(j).getNumeroArticulos() + total;

        }

        return total;

    }
    public int getItemCount(){
        return articulosLista.size();
    }

    public articulo getArticulo(int position){
        return articulosLista.get(position);
    }

    public void setArticulo(articulo nArticulo){
        articulosLista.add(0,nArticulo);
        notifyItemInserted(0);
    }

    public void removeItem(int position) {
         articulosLista.remove(position);
         notifyItemRemoved(position);
         notifyItemRangeChanged(position, articulosLista.size());
    }

}
