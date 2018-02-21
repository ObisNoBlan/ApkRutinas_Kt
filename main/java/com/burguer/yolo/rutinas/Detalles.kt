package com.burguer.yolo.rutinas

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.burguer.yolo.rutinas.Adapters.MyAdapterDos
import com.burguer.yolo.rutinas.Models.Ejercicios
import com.burguer.yolo.rutinas.Models.Rutina
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalles.*


class Detalles : AppCompatActivity() {
    var toolbar:Toolbar?=null
    var rutina:Rutina?=null
    var recycler:RecyclerView?=null
    var adapterRec:MyAdapterDos?=null
    var lista:ArrayList<Ejercicios>?=null
    var colapse: ImageView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)
        var disciplina=intent.extras["disciplina"].toString()
       lista=intent.getSerializableExtra("ejercicios") as ArrayList<Ejercicios>

        //color barra notificacions
        val window = getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.setStatusBarColor(getResources().getColor(R.color.colorBlac))
        //------------------------------------------------------------------------------------------
        //toolbar
        /*toolbar=findViewById(R.id.toolbarinfo)
        toolbar!!.setBackgroundColor(Color.BLACK)
        toolbar!!.setTitleTextColor(Color.RED)
        toolbar!!.setTitle("Detalles")
        setSupportActionBar(toolbar)*/
        var tvDia:TextView=findViewById(R.id.tvDiaColapse)
        tvDia.text=intent.extras["dia"].toString()
        var tvDuracion:TextView=findViewById(R.id.tvDuracionColapse)
        tvDuracion.text=intent.extras["duracion"].toString()
        var tvCreador:TextView=findViewById(R.id.tvCreator)
        tvCreador.text=intent.extras["creador"].toString()
        var tvDisci:TextView=findViewById(R.id.tvDesciplinaColapse)
        colapse = findViewById(R.id.imageDetail)
        if (disciplina.equals("Boxeo")){
            Picasso.with(this).load(R.drawable.boxeofondo).into(colapse)
            tvDisci.text="Boxeo"
        }else if(disciplina.equals("Muay-thai")){
            Picasso.with(this).load(R.drawable.muayfondo).into(colapse)
            tvDisci.text="Muay-Thai"
        }else if(disciplina.equals("Kick-Boxing")){
            Picasso.with(this).load(R.drawable.kickfondo).into(colapse)
            tvDisci.text="Kick-Boxing"
        }else if(disciplina.equals("Gimnasio")){
            Picasso.with(this).load(R.drawable.fitnessfondo).into(colapse)
            tvDisci.text="Gimnasio"
        }else if(disciplina.equals("Calistenia")){
            Picasso.with(this).load(R.drawable.calisteniafondo).into(colapse)
            tvDisci.text="Calistenia"
        }else if(disciplina.equals("Crossfit")){
            Picasso.with(this).load(R.drawable.crosfitfondo).into(colapse)
            tvDisci.text="Crossfit"
        }else if(disciplina.equals("Running")){
            Picasso.with(this).load(R.drawable.runingfondo).into(colapse)
            tvDisci.text="Running"
        }
        recycler=findViewById(R.id.recyclerDetail)
        recycler!!.layoutManager=LinearLayoutManager(this,LinearLayout.VERTICAL, false)
        adapterRec= MyAdapterDos(lista!!,this)
        recycler!!.adapter=adapterRec

        //------------------------------------------------------------------------------------------
    }

    override fun onBackPressed() {
        lista!!.clear()
        super.onBackPressed()

    }
}
