package com.burguer.yolo.rutinas

import android.app.AlertDialog
import android.content.ComponentCallbacks2
import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.WindowManager
import android.widget.*
import com.burguer.yolo.rutinas.Adapters.MyAdapter
import com.burguer.yolo.rutinas.Adapters.MyAdapterDos
import com.burguer.yolo.rutinas.BaseDatos.ConexionBD
import com.burguer.yolo.rutinas.Models.Ejercicios
import com.burguer.yolo.rutinas.Models.Rutina
import java.lang.reflect.Array


class Add : AppCompatActivity() {
    var listaEj= ArrayList<Ejercicios>()
    var spDias:Spinner?=null
    var recyclerAdd:RecyclerView?=null
    var adapradorRecy:MyAdapterDos?=null
    var datos:ArrayList<String>?=null
    var datosDos:ArrayList<String>?=null
    var adaptador: ListAdapter?=null
    var spDisciplinas:Spinner?=null
    var bAdd:Button?=null
    var bBorrar:Button?=null
    var etCreador:EditText?=null
    var etDuracion:EditText?=null
    var conexionBD:ConexionBD?=null
    var ejercicio:Ejercicios?=null
    var rutina:Rutina?=null
    var bGuardar:Button?=null
    private val p = Paint()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.setStatusBarColor(getResources().getColor(R.color.colorBlac))
        //varianles
        conexionBD= ConexionBD(this)
        bAdd=findViewById(R.id.bAdd)
        bGuardar= findViewById(R.id.bGuardar)
        bGuardar!!.setOnClickListener({
            var creador=etCreador!!.text.toString()
            var duracion=etDuracion!!.text.toString()
            var dia=spDias!!.selectedItem.toString()
            var disciplina=spDisciplinas!!.selectedItem.toString()
            var item=recyclerAdd!!.layoutManager.itemCount
            rutina= Rutina(dia,disciplina,duracion,creador,conexionBD!!.maximoId()+1)
            Log.e("HEEEE","maximoooooooooooooooooooooooooooooooooooooo "+conexionBD!!.maximoId()+1)
            if(conexionBD!!.insertarRutina(rutina!!)==-1L){

                Toast.makeText(this, "Error insertando", Toast.LENGTH_SHORT).show()
            }else{
               Toast.makeText(this, "Insertado correctamente", Toast.LENGTH_SHORT).show()
            }
            for (i in 0..item-1 ){
                ejercicio=listaEj[i]
                ejercicio!!.id=rutina!!.listaEj
                Log.e("ej","eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee "+ejercicio!!.nombre)
               // Toast.makeText(this, "nmaximoID ----"+(conexionBD!!.maximoId()), Toast.LENGTH_SHORT).show()
                if(conexionBD!!.insertarEjercicio(ejercicio!!)==-1L){
                   // Toast.makeText(this, "errorEj", Toast.LENGTH_SHORT).show()
                }else{
                    //Toast.makeText(this, "noerrorEj", Toast.LENGTH_SHORT).show()
                }
            }
            etCreador!!.text.clear()
            etDuracion!!.text.clear()
            listaEj.clear()
            recyclerAdd!!.layoutManager = LinearLayoutManager(this@Add, LinearLayout.VERTICAL, false)
            adapradorRecy = MyAdapterDos(listaEj, this@Add)
            recyclerAdd!!.adapter = adapradorRecy
            //actualizar
          /*  if(conexionBD!!.actualizarEj(conexionBD!!.maximoId()+1)==-1L){
                Toast.makeText(this, "errorActualizando"+(conexionBD!!.maximoId()+1), Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "noerrorACtu"+(conexionBD!!.maximoId()+1), Toast.LENGTH_SHORT).show()
            }*/


        })
        bBorrar=findViewById(R.id.bBorrar)
        etCreador=findViewById(R.id.etCreadorAdd)
        etDuracion=findViewById(R.id.etDuracionAdd)
        bBorrar!!.setOnClickListener({
            etCreador!!.text.clear()
            etDuracion!!.text.clear()
        })
        //rellenar spinner
        spDias=findViewById(R.id.spDias)
        var datos = arrayOf("Lunes","Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo")
        val adapter = ArrayAdapter(this,
                android.R.layout.simple_selectable_list_item, datos)
        spDias!!.adapter=adapter
       // adaptador= ListAdapter(this@Add,listaEj)
        spDisciplinas=findViewById(R.id.spDisciplinas)
        var datosdos= arrayOf("Boxeo","Muay-thai","Kick-Boxing","Gimnasio","Calistenia","Crossfit","Running")
        val adapterdos = ArrayAdapter(this,
                android.R.layout.simple_selectable_list_item, datosdos)
        spDisciplinas!!.adapter=adapterdos
        //------------------------------------------------------------------------------------------
        //recycler
        recyclerAdd= findViewById(R.id.recyclerAdd)
        /*recyclerAdd!!.layoutManager=LinearLayoutManager(this@Add,LinearLayout.VERTICAL, false)
        adapradorRecy=MyAdapterDos(listaEj,this@Add)
        recyclerAdd!!.adapter=adapradorRecy*/
        initSwipe()
        //------------------------------------------------------------------------------------------
        bAdd!!.setOnClickListener({
            val inflater = getLayoutInflater()
            val dialoglayout = inflater.inflate(R.layout.dialog_add, null)
            val builder = AlertDialog.Builder(this)
            var etDescri:TextView=dialoglayout.findViewById(R.id.etDescrip)
            var etRepe:TextView=dialoglayout.findViewById(R.id.etRepe)
            var etSeries:TextView=dialoglayout.findViewById(R.id.etSeri)
            var bAceptar:Button=dialoglayout.findViewById(R.id.bAceptar)

            bAceptar.setOnClickListener({
                if(etDescri.text.isEmpty() || etRepe.text.isEmpty() || etSeries.text.isEmpty()){
                    Toast.makeText(this,"Rellene los campos vacios",Toast.LENGTH_SHORT).show()
                }else{
                    try {
                        // var repeticiones:Int=etRepe.text.toString().toInt()
                        listaEj.add(Ejercicios(0, etDescri.text.toString(), etRepe.text.toString().toInt(), etSeries.text.toString().toInt()))
                        recyclerAdd!!.layoutManager = LinearLayoutManager(this@Add, LinearLayout.VERTICAL, false)
                        adapradorRecy = MyAdapterDos(listaEj, this@Add)
                        recyclerAdd!!.adapter = adapradorRecy
                        etDescri.text=""
                        etSeries.text=""
                        etRepe.text=""
                    }catch (e:NumberFormatException){Toast.makeText(this,"Repeticiones y Series deben ser numéricos", Toast.LENGTH_SHORT).show()}
                }
            })
            builder.setView(dialoglayout)
            builder.show()
        })

    }
    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT) {
                    //adapter!!.removeItem(position)
                    listaEj!!.removeAt(position)
                    adapradorRecy= MyAdapterDos(listaEj!!,this@Add)
                    recyclerAdd!!.layoutManager= LinearLayoutManager(this@Add,LinearLayout.VERTICAL, false)
                    recyclerAdd!!.adapter=adapradorRecy
                    adapradorRecy!!.notifyDataSetChanged()
                } else {
                    listaEj!!.removeAt(position)
                    adapradorRecy= MyAdapterDos(listaEj!!,this@Add)
                    recyclerAdd!!.layoutManager= LinearLayoutManager(this@Add,LinearLayout.VERTICAL, false)
                    recyclerAdd!!.adapter=adapradorRecy
                    adapradorRecy!!.notifyDataSetChanged()
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                var icon= BitmapFactory.decodeResource(resources,R.drawable.papelera)
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3
                    if (dX > 0) {
                        p.color = Color.parseColor("#FFFFFF")
                        val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), dX, itemView.bottom.toFloat())
                        c.drawRect(background, p)
                        // icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete_white)
                        val icon_dest = RectF(itemView.left.toFloat() + width, itemView.top.toFloat() + width, itemView.left.toFloat() + 2 * width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, icon_dest, p)
                    } else {
                        p.color = Color.parseColor("#FFFFFF")
                        val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                        c.drawRect(background, p)
                        // icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete_white)
                        val icon_dest = RectF(itemView.right.toFloat() - 2 * width, itemView.top.toFloat() + width, itemView.right.toFloat() - width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, icon_dest, p)


                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerAdd)
    }
    private fun removeView() {
        /*if (view!!.parent != null) {
            (view!!.parent as ViewGroup).removeView(view)
        }*/
    }

    private fun initDialog() {
        /* alertDialog = AlertDialog.Builder(this)
         view = layoutInflater.inflate(R.layout.dialog_layout, null)
         alertDialog!!.setView(view)
         alertDialog!!.setPositiveButton("Save") { dialog, which ->
             if (add) {
                 add = false
                 adapter!!.addItem(et_name!!.text.toString())
                 dialog.dismiss()
             } else {
                 names[edit_position] = et_name!!.text.toString()
                 adapter!!.notifyDataSetChanged()
                 dialog.dismiss()
             }
         }
         et_name = view!!.findViewById(R.id.et_name) as EditText*/
    }
}
