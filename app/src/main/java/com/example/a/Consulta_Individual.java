package com.example.a;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a.utilidades.Utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Consulta_Individual extends AppCompatActivity {
    EditText campoId;
    EditText campoTodo;
    TextView campoData;
    CheckBox campoComplete;
    ConexionSQLiteHelper conn;
    String check;
    String comprobar_check_bd;
    boolean a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta__individual);

        /**Establezco la conexión**/
        conn = new ConexionSQLiteHelper(this, "bd_objeto", null, 1);

        campoId = (EditText) findViewById(R.id.idCampoId);
        campoTodo = (EditText) findViewById(R.id.id_Todo);
        campoData = (TextView) findViewById(R.id.id_fecha);
        campoComplete = (CheckBox) findViewById(R.id.idComplete);


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idBotonBuscar:
                consultar();
                break;
            case R.id.idBotonActualizar:
                actualizar();
                break;
            case R.id.idBotonEliminar:
                eliminar();
                break;
        }
    }

    /**Este metodo es el que hace los selects a la base de datos**/
    private void consultar() {
        /**me pongo a leerla con el db**/
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {campoId.getText().toString()};
        String[] campos = {Utilidades.CAMPO_TODO, Utilidades.CAMPO_DATE, Utilidades.CAMPO_COMPLETE};

        try {
            /** luego voy metiendo todas las cosas en el curso**/
            Cursor cursor = db.query(Utilidades.TABLA_TODO, campos, Utilidades.CAMPO_ID + "=?", parametros, null, null, null);
            cursor.moveToFirst();
            /**Lo que haya en la posición 2 que es el valor del Check box en formato String
             * se lo paso a la variable comprobar_check_bd que es de tipo string
             * a continuación lo paso por el método**/
            comprobar_check_bd=cursor.getString(2);
            comprobar(comprobar_check_bd);

            /**cada campo coge sus datos de la bd**/
            campoTodo.setText(cursor.getString(0));
            campoData.setText(cursor.getString(1));
            /** a valdrá true o false y dirá si se pinta o no el check**/
            campoComplete.setChecked(a);

            //Toast.makeText(getApplicationContext(), "a= " +a+" comprobarCheck= "+comprobar_check_bd, Toast.LENGTH_LONG).show();

            cursor.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "El documento no existe", Toast.LENGTH_LONG).show();
            limpiar();

        }

    }
/**Este metodo recibe el String comprobar_check_bd y mira si su contenido es igual a true
 * si lo es hace que el boolean a valga true sino pues false**/
    private void comprobar(String comprobar_check_bd) {
        if(comprobar_check_bd.equals("true")){
            a=true;
        }else{
            a=false;
        }

    }
/**Este método coge lo que el usuario quiera cambiar y lo guarda en la base de datos**/
    private void actualizar() {
        /**Lo primero que hace el validar**/
        validar();
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {campoId.getText().toString()};
        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_TODO, campoTodo.getText().toString());
        values.put(Utilidades.CAMPO_DATE,getDataTime());
        /**check valdrá true o false en formato string para guardarlo en la bd**/
        values.put(Utilidades.CAMPO_COMPLETE,check);

        db.update(Utilidades.TABLA_TODO, values, Utilidades.CAMPO_ID + "=?", parametros);

        Toast.makeText(getApplicationContext(), "Se ha actualizado "+ values, Toast.LENGTH_LONG).show();
        db.close();
    }

/**Mira si el campo está checkeado si lo está, la variable de tipo string check valdrá true**/
    private void validar() {
        if(campoComplete.isChecked()){
            check="true";
        }else{
            check="false";
        }
    }

/**Elimina de la base de datos al objeto**/
    private void eliminar() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {campoId.getText().toString()};

        db.delete(Utilidades.TABLA_TODO, Utilidades.CAMPO_ID + "=?", parametros);
        Toast.makeText(getApplicationContext(), "Se ha eliminado", Toast.LENGTH_LONG).show();

        //para borrar el campoId
        campoId.setText("");
        // para borrar los otros campos
        limpiar();
        db.close();
    }
    /**Limpia los campos**/
    private void limpiar() {
        campoTodo.setText("");
        campoData.setText("");
        campoComplete.setChecked(false);
    }

    /**Este método devuelve la fecha del sistema**/
    private String getDataTime(){
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date=new Date();
        return dateFormat.format(date);

    }

}
