package com.burguer.yolo.rutinas.SplashScreen

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Window
import com.burguer.yolo.rutinas.R
import com.burguer.yolo.rutinas.MainActivity
import android.content.Intent
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.util.*
import android.view.WindowManager




/**
 * Created by onbh4 on 14/02/2018.
 */
class Splash : Activity() {
    val tiempo:Long =2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.setStatusBarColor(getResources().getColor(R.color.colorBlac))

        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.splash_screen)
        val task = object : TimerTask() {
            override fun run() {
                val mainIntent = Intent().setClass(
                        this@Splash, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        }
        val timer = Timer()
        timer.schedule(task, tiempo)
    }

}