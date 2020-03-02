package com.example.a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.a.adapatadores.ListaObjetoAdapter;
import com.example.a.entidades.Objeto;
import com.example.a.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Collections;

public class ConsultaRecycler extends AppCompatActivity {

    ArrayList<Objeto>listaObjeto;
    RecyclerView recyclerViewObjeto;
    String comprobar_check_bd;
    boolean a;
    ConexionSQLiteHelper conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_recycler);

        conn = new ConexionSQLiteHelper(this, "bd_objeto", null, 1);

        listaObjeto= new ArrayList<>();

        recyclerViewObjeto=(RecyclerView)findViewById(R.id.recyclerObjeto);
        recyclerViewObjeto.setLayoutManager(new LinearLayoutManager(this));

        consultarListaObjeto();

       final ListaObjetoAdapter adapter=new ListaObjetoAdapter(listaObjeto);

       adapter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(getApplicationContext(),"Seleccion: "+listaObjeto.get(recyclerViewObjeto.getChildAdapterPosition(v)).getToDo(),Toast.LENGTH_SHORT).show();

            Objeto objeto = listaObjeto.get(recyclerViewObjeto.getChildAdapterPosition(v));
            Intent intent= new Intent(ConsultaRecycler.this,DetalleObjeto.class);

            Bundle bundle=new Bundle();
            bundle.putSerializable("objeto",objeto);

            intent.putExtras(bundle);
            startActivity(intent);
           }
       });


       recyclerViewObjeto.setAdapter(adapter);

        borrarDeslizar(adapter);
    }

    private void consultarListaObjeto() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Objeto objeto= null;

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_TODO,null);

        while(cursor.moveToNext()){
            objeto=new Objeto();
            objeto.setToDo(cursor.getString(1));
            objeto.setDate(cursor.getString(2));
            objeto.setComplete(cursor.getString(3));

           /* comprobar_check_bd=objeto.setComplete(cursor.getString(3));
            comprobar(comprobar_check_bd);*/

            listaObjeto.add(objeto);
        }
    }
    private void comprobar(String comprobar_check_bd) {
        if(comprobar_check_bd.equals("true")){
            a=true;
        }else{
            a=false;
        }

    }
    public void borrarDeslizar(final ListaObjetoAdapter adapter){
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                ItemTouchHelper.DOWN | ItemTouchHelper.UP, ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT)  {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                int from = viewHolder.getAdapterPosition();
                int to = viewHolder1.getAdapterPosition();
                Collections.swap(listaObjeto, from, to);
                adapter.notifyItemMoved(from, to);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                SQLiteDatabase db=conn.getWritableDatabase();
                Integer id = listaObjeto.get(viewHolder.getAdapterPosition()).getId();
                String[] parametros = {String.valueOf(id)};

                db.delete(Utilidades.TABLA_TODO,Utilidades.CAMPO_ID+"=?",parametros);
                Toast.makeText(getApplicationContext(),"Se ha eliminado",Toast.LENGTH_LONG).show();

                listaObjeto.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        helper.attachToRecyclerView(recyclerViewObjeto);
    }







}
