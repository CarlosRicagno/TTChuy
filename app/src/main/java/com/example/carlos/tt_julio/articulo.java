package com.example.carlos.tt_julio;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Carlos on 07/02/2017.
 */
public class articulo implements Parcelable {
    public String nombreArticulo;
    public int numeroArticulos;
    public float precioUnit;
    public float precioTotal;
    public int codigoBarras;

    public articulo() {
        this.nombreArticulo = "vacio";
        this.numeroArticulos = 1;
        this.precioUnit = 100;
        this.precioTotal = 1 * precioUnit;
        this.codigoBarras = 750456;
        //super();

    }

    public articulo(String nombreArticulo, int numeroArticulo, float precioUnit, float precioTotal, int codigo) {
        //super();
        this.nombreArticulo = nombreArticulo;
        this.numeroArticulos = numeroArticulo;
        this.precioUnit = precioUnit;
        this.precioTotal = precioTotal;
        this.codigoBarras = codigo;
    }

    public String getNombreArticulo() {
        return this.nombreArticulo;
    }

    public void setNombreArticulo(String articulo) {
        this.nombreArticulo = articulo;
    }

    public int getNumeroArticulos() {
        return this.numeroArticulos;

    }

    public void setNumeroArticulos(int numero) {
        this.numeroArticulos = numero;
    }

    public float getPrecioUnit() {
        return this.precioUnit;

    }

    public void setPrecioUnit(float precio) {
        this.precioUnit = precio;
    }
    public float getPrecioTotal() {
        return this.precioTotal;

    }

    public void setPrecioTotal(float precio) {
        this.precioTotal = precio;
    }

    public int getCodigoBarras() {
        return this.codigoBarras;

    }

    public void setCodigoBarras(int numero) {
        this.codigoBarras = numero;
    }

    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags){


        dest.writeString(nombreArticulo);
        dest.writeInt(numeroArticulos);
        dest.writeFloat(precioUnit);
        dest.writeFloat(precioTotal);
        dest.writeInt(codigoBarras);
    }
    public static final Creator CREATOR = new Creator(){
        public articulo createFromParcel(Parcel in){
            return new articulo(in);
        }
        public articulo[] newArray(int size){
            return new articulo[size];
        }
    };
    public articulo(Parcel in){
        this.nombreArticulo = in.readString();
        this.numeroArticulos = in.readInt();
        this.precioUnit = in.readFloat();
        this.precioTotal = in.readFloat();
        this.codigoBarras = in.readInt();
    }

}
