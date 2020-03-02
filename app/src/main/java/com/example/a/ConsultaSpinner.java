package com.example.a;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a.entidades.Objeto;
import com.example.a.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultaSpinner extends AppCompatActivity {

    Spinner spinnerObjetos;
    TextView txtToDo, txtFecha;
    CheckBox complete;
    ArrayList<String> listaObjeto;
    ArrayList<Objeto> objetoList;
    ConexionSQLiteHelper conn;
    String comprobar_check_bd;
    boolean a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_spinner);

        conn = new ConexionSQLiteHelper(this, "bd_objeto", null, 1);

        spinnerObjetos = (Spinner) findViewById(R.id.idspinner);
        txtToDo = (TextView) findViewById(R.id.idContenidoCampoTodo);
        txtFecha = (TextView) findViewById(R.id.idContenidoCampoFecha);
        complete = (CheckBox) findViewById(R.id.idContenidocheck);

        consultarListaObjeto();

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaObjeto);

        spinnerObjetos.setAdapter(adaptador);

        spinnerObjetos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idl) {
                if (position != 0) {


                    Toast.makeText(getApplicationContext(), "a= "+a+"comprobar_check= "+comprobar_check_bd, Toast.LENGTH_LONG).show();
                    txtToDo.setText(objetoList.get(position - 1).getToDo());
                    txtFecha.setText(objetoList.get(position - 1).getDate());
                    complete.setChecked(a);

                }else {
                    limpiar();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void consultarListaObjeto() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Objeto objetoTodo = null;
        objetoList = new ArrayList<Objeto>();

        //select * from objeto
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_TODO, null);



        //moveToNext hace que si hay más registros siga haciendo el bucle
        while (cursor.moveToNext()) {
            objetoTodo = new Objeto();
           /*comprobar_check_bd=cursor.getString(3);
            comprobar(comprobar_check_bd);*/

            objetoTodo.setToDo(cursor.getString(1));
            objetoTodo.setDate(cursor.getString(2));
            objetoTodo.setComplete(cursor.getString(3));

            objetoList.add(objetoTodo);

        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaObjeto = new ArrayList<String>();
        listaObjeto.add("Seleccione");

        for (int i = 0; i < objetoList.size(); i++) {
            comprobar_check_bd=objetoList.get(i).getComplete();
            comprobar(comprobar_check_bd);

            // de la lista de personas obtener del primer objeto el Id y concatene con el nombre
            listaObjeto.add(objetoList.get(i).getToDo());
        }
    }


    private void comprobar(String comprobar_check_bd) {
        if (comprobar_check_bd.equals("true")) {
            a = true;
        } else {
            a = false;
        }

    }

    /**Limpia los campos**/
    private void limpiar() {
        txtToDo.setText("");
        txtFecha.setText("");
        complete.setChecked(false);
    }
}
