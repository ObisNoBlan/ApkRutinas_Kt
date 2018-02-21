package com.burguer.yolo.rutinas.Adapters

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.burguer.yolo.rutinas.BaseDatos.ConexionBD
import com.burguer.yolo.rutinas.Detalles
import com.burguer.yolo.rutinas.Models.Ejercicios
import com.burguer.yolo.rutinas.Models.Rutina
import com.burguer.yolo.rutinas.R

/**
 * Created by onbh4 on 14/02/2018.
 */
class MyAdapter(val listaR:ArrayList<Rutina>,val activity: Activity ) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_recycler, parent, false)
        return ViewHolder(v, activity)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listaR[position])
    }

    override fun getItemCount(): Int {
       return listaR.size;
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView){
        var conexionBD:ConexionBD= ConexionBD(activity)

        fun bindItems(rutina: Rutina){
            val tvDia = itemView.findViewById<TextView>(R.id.tvDia)
            val tvHoras = itemView.findViewById<TextView>(R.id.tvHoras)
            val tvDisciplina = itemView.findViewById<TextView>(R.id.tvDisciplina)
            val tvEj= itemView.findViewById<TextView>(R.id.tvEj)
            tvDia.text=rutina.dia
            tvEj.text= rutina.listaEj.toString()
            tvHoras.text=rutina.duracion
            tvDisciplina.text=rutina.disciplina
            itemView.setOnClickListener({
                var intent:Intent = Intent(activity, Detalles::class.java)
                intent.putExtra("disciplina",rutina.disciplina)
                intent.putExtra("creador",rutina.creador)
                intent.putExtra("duracion",rutina.duracion)
                intent.putExtra("dia",rutina.dia)
                var listEjercicios=conexionBD.obtenerEjerciciosRutinas(rutina.listaEj)

                Log.e("HOLAAAAAA","NUMERO --------------------"+ rutina.listaEj)
                intent.putExtra("ejercicios", listEjercicios)
                activity.startActivity(intent)
            })
        }
    }
}