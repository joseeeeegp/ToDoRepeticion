package com.example.a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a.utilidades.Utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Registrar extends AppCompatActivity {

EditText campoId, campoToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        campoId = (EditText) findViewById(R.id.idToDo);
        campoToDo=(EditText) findViewById(R.id.idEscribaToDo);
    }

    public void onClick(View view){
        registrarObjeto();
    }

    private void registrarObjeto() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_objeto", null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ID, campoId.getText().toString());
        values.put(Utilidades.CAMPO_TODO, campoToDo.getText().toString());
        values.put(Utilidades.CAMPO_DATE,getDataTime());
        /**el valor del check se guarda como string**/
        values.put(Utilidades.CAMPO_COMPLETE,"false");

        Long idResultante = db.insert(Utilidades.TABLA_TODO, Utilidades.CAMPO_ID, values);

        //Toast.makeText(getApplicationContext(), "Id Registro: " + idResultante, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Se ha realizado el registro",Toast.LENGTH_SHORT).show();
        db.close();

    }
    /**Este método devuelve la fecha del sistema**/
    private String getDataTime(){
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date=new Date();
        return dateFormat.format(date);

    }
}
