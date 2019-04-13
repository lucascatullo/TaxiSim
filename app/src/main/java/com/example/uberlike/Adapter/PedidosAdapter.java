package com.example.uberlike.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uberlike.R;

import java.util.ArrayList;


public class PedidosAdapter extends BaseAdapter {

    public ArrayList<Pedido> ArrayPedidos ;
    public Context context;

    public PedidosAdapter(Context context, ArrayList<Pedido> ArrayPedidos) {
        this.context = context;
        this.ArrayPedidos = ArrayPedidos;
    }

    @Override
    public int getCount() {
        return ArrayPedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return ArrayPedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.lista_pedidos, null);
        TextView textOrigen= convertView.findViewById(R.id.textView17);
        TextView textDestino= convertView.findViewById(R.id.textView18);
        TextView textEstado= convertView.findViewById(R.id.textView19);
        textOrigen.setText(ArrayPedidos.get(position).origen);
        textDestino.setText(ArrayPedidos.get(position).destino);
        textEstado.setText(ArrayPedidos.get(position).status);
        return convertView;
    }

    public static class Pedido {
        public String destino,origen, status,email;
    }

}
