package com.burguer.yolo.rutinas.Adapters

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.burguer.yolo.rutinas.Detalles
import com.burguer.yolo.rutinas.Models.Ejercicios
import com.burguer.yolo.rutinas.Models.Rutina
import com.burguer.yolo.rutinas.R

/**
 * Created by onbh4 on 15/02/2018.
 */
class MyAdapterDos(val listaR:ArrayList<Ejercicios>, val activity: Activity) : RecyclerView.Adapter<MyAdapterDos.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_list, parent, false)
        return ViewHolder(v, activity)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listaR[position])
    }

    override fun getItemCount(): Int {
        return listaR.size;
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView){
        fun bindItems(ejercicio: Ejercicios){
            var tvNombre:TextView=itemView.findViewById(R.id.tvNombre2)
            var tvRepeticiones:TextView=itemView.findViewById(R.id.tvRepeticiones2)
            var tvSeries:TextView= itemView.findViewById(R.id.tvSeries2)
            tvNombre.text=ejercicio.nombre
            tvSeries.text= ejercicio.series.toString()
            tvRepeticiones.text=ejercicio.repeticiones.toString()
            itemView.setOnClickListener({

            })
        }
    }
}