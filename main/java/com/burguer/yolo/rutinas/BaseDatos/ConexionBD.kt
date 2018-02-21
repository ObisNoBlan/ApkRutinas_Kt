package com.burguer.yolo.rutinas.BaseDatos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.burguer.yolo.rutinas.Models.Ejercicios
import com.burguer.yolo.rutinas.Models.Rutina

/**
 * Created by onbh4 on 15/02/2018.
 */
class ConexionBD(var context: Context): SQLiteOpenHelper(context,"BD_rutinas.db",null,19) {
    var nombreBD="Rutinas"
    var tablaEjercicios="CREATE TABLE ejercicios (id INT ,\n" +
            "nombre VARCHAR(30), repeticiones INT, series INT)"
    var tablaRutina="CREATE TABLE rutinas (id INTEGER PRIMARY KEY ,\n" +
            "dia VARCHAR(20), disciplina VARCHAR(30),duracion VARCHAR(30), creador VARCHAR(100), ejercicios\n" +
            "INT,FOREIGN KEY(ejercicios) REFERENCES ejercicios(id))"
    var insert="insert into rutinas values(1,\'Lunes\',\'Boxeo\',\'1\',\'Alejandro martinez\',1)"
    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(tablaEjercicios)
        p0!!.execSQL(tablaRutina)
       // p0!!.execSQL(insert)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS" + " ejercicios");
        p0!!.execSQL("DROP TABLE IF EXISTS" + " rutinas");
        onCreate(p0);
    }
    fun actualizarEj(id:Int):Long{
        var nreg_afectados=-1L
        var db:SQLiteDatabase= writableDatabase
        if(db!=null){
            var valores:ContentValues= ContentValues()
            valores.put("ejercicios",id)
            //nreg_afectados= db.update("rutinas",valores,"id="+id,null).toLong()
            db.rawQuery("update rutinas set ejercicios ="+id+" where id="+1,null,null)
        }
        return nreg_afectados
    }
    fun insertarRutina(r: Rutina):Long{
        var nreg_afectados=-1L

        var db:SQLiteDatabase= writableDatabase
        if(db!=null){
            var valores:ContentValues= ContentValues()
            valores.put("id", (maximoId()+1))
            valores.put("dia", r.dia)
            valores.put("disciplina",r.disciplina)
            valores.put("duracion",r.duracion)
            valores.put("creador",r.creador)
            valores.put("ejercicios", r.listaEj)

            nreg_afectados= db.insert("rutinas",null,valores)
        }
        db.close()
        return nreg_afectados
    }
    fun insertarEjercicio(e:Ejercicios):Long{
        var nreg_afectados=-1L

        var db:SQLiteDatabase= writableDatabase
        if(db!=null){
            var valores:ContentValues= ContentValues()
            valores.put("id",e.id)
            valores.put("nombre", e.nombre)
            valores.put("repeticiones",e.repeticiones)
            valores.put("series",e.series)

            nreg_afectados= db.insert("ejercicios",null,valores)
        }
        db.close()
        return nreg_afectados
    }
    fun borrarRutina(id:Int):Long{
        var nreg_afectados=-1L
        var db:SQLiteDatabase=writableDatabase
        if(db!=null){
            nreg_afectados= db.delete("rutinas","id="+id, null).toLong()
        }
        return nreg_afectados
    }
    fun borrarEjercicio(id:Int):Long{
        var nreg_afectados=-1L
        var db:SQLiteDatabase=writableDatabase
        if(db!=null){
            nreg_afectados= db.delete("ejercicios","id="+id, null).toLong()
        }
        return nreg_afectados
    }
    fun maximoId():Int{
        var max:Int?=null
        var db:SQLiteDatabase=writableDatabase
        var c:Cursor=db.rawQuery("Select max(id) from rutinas",null,null)
        if(c.moveToNext()){
                max=c.getInt(0)
        }
        return max!!
    }
    fun obtenerRutinas():ArrayList<Rutina>{
        var lista:ArrayList<Rutina>?=null
        lista=ArrayList()
        var listaEj:ArrayList<Ejercicios>?=null
        var db:SQLiteDatabase=writableDatabase
        if(db!=null){
            var campos= arrayOf("id","dia","disciplina","duracion","creador","ejercicios")


            var c:Cursor=db.query("rutinas", campos,null,null,null,null,null,null)
            if(c.moveToFirst()){
                do{
                    lista!!.add(Rutina(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5)))
                }while(c.moveToNext())
            }
        }
        db.close()
        return lista!!
    }
    fun obtenerRutinasFiltro(disci:String):ArrayList<Rutina>{
        var lista:ArrayList<Rutina>?=null
        lista=ArrayList()
        var listaEj:ArrayList<Ejercicios>?=null
        var db:SQLiteDatabase=writableDatabase
        if(db!=null){
            var campos= arrayOf("id","dia","disciplina","duracion","creador","ejercicios")


            var c:Cursor=db.query("rutinas", campos,"disciplina=\'"+disci+"\'",null,null,null,null,null)
            if(c.moveToFirst()){
                do{
                    lista!!.add(Rutina(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5)))
                }while(c.moveToNext())
            }
        }
        db.close()
        return lista!!
    }
    fun obtenerEjerciciosRutinas(id:Int):ArrayList<Ejercicios>{
        var lista:ArrayList<Ejercicios>?=null
        lista= ArrayList()
        var db:SQLiteDatabase=writableDatabase
            var campos= arrayOf("id","nombre", "repeticiones","series")
        var c:Cursor=db.query("ejercicios", campos,"id="+id,null,null,null,null,null)
            if(c.moveToNext()){
                do {
                    Log.e("HOLA", "QUE SI TENGO DATOS------------------------------------------------------------------------")
                    lista!!.add(Ejercicios(c.getInt(0), c.getString(1),c.getInt(2),c.getInt(3)))
                }while(c.moveToNext())
            }
        for (i in 0..lista!!.size-1){
            var ej=lista!![i]
            Log.e("g","---------------------------NOMBRE--------------------------"+ej.nombre)
        }
        return lista!!
    }
}