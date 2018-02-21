package com.burguer.yolo.rutinas

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.burguer.yolo.rutinas.Models.Rutina
import com.burguer.yolo.rutinas.Adapters.MyAdapter
import com.burguer.yolo.rutinas.BaseDatos.ConexionBD
import com.burguer.yolo.rutinas.Models.Ejercicios
import android.widget.AdapterView
import android.widget.Toast



class MainActivity:AppCompatActivity() {
    var hasEj= ArrayList<Ejercicios>()
    var adapter:MyAdapter?=null
    var recycler:RecyclerView?=null
    private val p = Paint()
    var listaR:ArrayList<Rutina>?=null
    var menuItem:MenuItem?=null
    var spinner: Spinner?=null
    var datosDis:ArrayList<String>?=null
    var conexionBD:ConexionBD?=null
    var swipe:SwipeRefreshLayout?=null
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hasEj!!.add(Ejercicios(1,"Press de banca",10,5))
        hasEj!!.add(Ejercicios(1,"Press de hombros",10,5))
        hasEj!!.add(Ejercicios(1,"sentadillas",10,5))
        hasEj!!.add(Ejercicios(1,"Dominadas",10,5))
        hasEj!!.add(Ejercicios(1,"Curl de biceps",10,5))
        conexionBD=ConexionBD(this)
        swipe=findViewById(R.id.swipe)
        swipe!!.setOnRefreshListener {
            Log.e("yaaaaaa","REFRESCOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO")
            listaR=conexionBD!!.obtenerRutinas()
            adapter=MyAdapter(listaR!!,this)
            recycler!!.layoutManager= LinearLayoutManager(this,LinearLayout.VERTICAL, false)
            recycler!!.adapter=adapter
            swipe!!.isRefreshing=false
        }
                //color barra notificacions
        val window = getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.setStatusBarColor(getResources().getColor(R.color.colorBlac))
        //------------------------------------------------------------------------------------------
        //toolbar
        val toolbar:Toolbar=findViewById(R.id.toolbardos)
        toolbar.setBackgroundColor(Color.BLACK)
        toolbar.setTitleTextColor(Color.RED)
        setSupportActionBar(toolbar)
        //------------------------------------------------------------------------------------------

        //recycler
        recycler = findViewById(R.id.recycler);
        listaR=conexionBD!!.obtenerRutinas()
        adapter=MyAdapter(listaR!!,this)
        adapter=MyAdapter(listaR!!,this)
        recycler!!.layoutManager= LinearLayoutManager(this,LinearLayout.VERTICAL, false)
        recycler!!.adapter=adapter
        adapter!!.notifyDataSetChanged()
        initSwipe()
        //borrar elemento de recycler al deslizar

        //------------------------------------------------------------------------------------------
        //floatting button
        val fabAdd:FloatingActionButton=findViewById(R.id.fabAdd)
        fabAdd.setOnClickListener({
            var intent: Intent = Intent(this@MainActivity,Add::class.java)
            startActivity(intent)

        })
        //------------------------------------------------------------------------------------------

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

                    var ruti=listaR!!.get(position)
                    conexionBD!!.borrarRutina(ruti.listaEj)
                    Log.e("dd", "NUMEROOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOo "+ruti!!.listaEj)
                    listaR=conexionBD!!.obtenerRutinas()
                    adapter=MyAdapter(listaR!!,this@MainActivity)
                    recycler!!.layoutManager= LinearLayoutManager(this@MainActivity,LinearLayout.VERTICAL, false)
                    recycler!!.adapter=adapter
                    adapter!!.notifyDataSetChanged()
                } else {

                    var ruti=listaR!!.get(position)
                    conexionBD!!.borrarRutina(ruti.listaEj)
                    listaR=conexionBD!!.obtenerRutinas()
                    adapter=MyAdapter(listaR!!,this@MainActivity)
                    recycler!!.layoutManager= LinearLayoutManager(this@MainActivity,LinearLayout.VERTICAL, false)
                    recycler!!.adapter=adapter
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                var icon= BitmapFactory.decodeResource(resources,R.drawable.papelera)
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3
                    if (dX > 0) {
                        p.color = Color.parseColor("#FFF3F5F6")
                        val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), dX, itemView.bottom.toFloat())
                        c.drawRect(background, p)
                       // icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete_white)
                        val icon_dest = RectF(itemView.left.toFloat() + width, itemView.top.toFloat() + width, itemView.left.toFloat() + 2 * width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, icon_dest, p)
                    } else {
                        p.color = Color.parseColor("#FFF3F5F6")
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
        itemTouchHelper.attachToRecyclerView(recycler)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var datosdos= arrayOf("Todo","Boxeo","Muay-thai","Kick-Boxing","Gimnasio","Calistenia","Crossfit","Running")
        val inflater=menuInflater
        inflater.inflate(R.menu.menu,menu)
        menuItem=menu!!.findItem(R.id.spinnerTB)
        spinner= menuItem!!.actionView as Spinner
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(spn: AdapterView<*>,
                                        v: android.view.View,
                                        posicion: Int,
                                        id: Long) {
                if(spinner!!.selectedItem.equals("Todo")){
                    listaR=conexionBD!!.obtenerRutinas()
                    adapter=MyAdapter(listaR!!,this@MainActivity)
                    recycler!!.layoutManager= LinearLayoutManager(this@MainActivity,LinearLayout.VERTICAL, false)
                    recycler!!.adapter=adapter
                }else{
                    listaR=conexionBD!!.obtenerRutinasFiltro(spinner!!.selectedItem.toString())
                    adapter=MyAdapter(listaR!!,this@MainActivity)
                    recycler!!.layoutManager= LinearLayoutManager(this@MainActivity,LinearLayout.VERTICAL, false)
                    recycler!!.adapter=adapter
                }
            }

            override fun onNothingSelected(spn: AdapterView<*>) {}
        };
        spinner!!.setBackground(resources.getDrawable(R.drawable.shapespinner))
        val adapterdos = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, datosdos)
        spinner!!.adapter=adapterdos
        return super.onCreateOptionsMenu(menu)
    }

}
