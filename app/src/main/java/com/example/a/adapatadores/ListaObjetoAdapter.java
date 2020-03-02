package com.example.a.adapatadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a.R;
import com.example.a.entidades.Objeto;

import java.util.ArrayList;

public class ListaObjetoAdapter
        extends RecyclerView.Adapter<ListaObjetoAdapter.ObjetoViewHolder>
        implements View.OnClickListener {

    String comprobar_check_bd;
    boolean a;

    ArrayList<Objeto> listaObjeto;
    private View.OnClickListener listener;

    public ListaObjetoAdapter(ArrayList<Objeto> listaObjeto) {
        this.listaObjeto = listaObjeto;
    }

    @Override
    public ObjetoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_objeto, null, false);

        view.setOnClickListener(this);

        return new ObjetoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ObjetoViewHolder holder, int position) {
        holder.contenidoTodo.setText(listaObjeto.get(position).getToDo());
        holder.contenidoFecha.setText(listaObjeto.get(position).getDate());
       holder.contenidoCheck.setText(listaObjeto.get(position).getComplete());
       // holder.contenidoCheck.setChecked(a);


    }

    @Override
    public int getItemCount() {
        return listaObjeto.size();
    }


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public class ObjetoViewHolder extends RecyclerView.ViewHolder {

        TextView contenidoTodo, contenidoFecha;
        CheckBox contenidoCheck;

        public ObjetoViewHolder(View itemView) {
            super(itemView);
            contenidoTodo = (TextView) itemView.findViewById(R.id.idContenidoTodo);
            contenidoFecha = (TextView) itemView.findViewById(R.id.idContenidoFecha);
            contenidoCheck = (CheckBox) itemView.findViewById(R.id.idContenidoCheck);

        }
    }
}
