package com.example.a.utilidades;

public class Utilidades {
    /**Constantes campos tabla usuario**/

    public static String TABLA_TODO="objetoToDo";
    public static String CAMPO_ID="id";
    public static String CAMPO_TODO="toDo";
    public static String CAMPO_DATE="date";
    public static String CAMPO_COMPLETE="complete";

    public static final String CREAR_TABLA_TODO="CREATE TABLE "+TABLA_TODO
            +" ("+CAMPO_ID+" INTEGER, "
            +CAMPO_TODO+" TEXT, "
            +CAMPO_DATE+" TEXT, "
            +CAMPO_COMPLETE+" TEXT)";

}
