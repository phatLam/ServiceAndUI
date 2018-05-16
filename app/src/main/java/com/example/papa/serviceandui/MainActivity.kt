package com.example.papa.serviceandui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        val ACION_MAIN = "music"
    }
    private val music = "NguoiAmPhu.mp3"
    private val musicReceiver = MusicReceiver()
//    val intent = Intent(this, MusicService::class.java)
//    val intent by lazy { Intent(this, MusicService::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_reset.setOnClickListener { onClick(it) }
        btn_start.setOnClickListener { onClick(it) }
        val filter = IntentFilter(ACION_MAIN)
        registerReceiver(musicReceiver, filter)
    }

    fun  onClick(view: View){
        var intent = Intent(this, MusicService::class.java)
        when (view.id){
            R.id.btn_start ->{intent.putExtra("type", 1)}
            R.id.btn_reset ->{intent.putExtra("type", 2)}
        }
        startService(intent)
    }

    ////////////////
    class MusicReceiver: BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {

            p1?.let {
                val num: Int = it.getIntExtra("num", -1)
                val state: Int = it.getIntExtra("state", -1)
            if (num > -1 )
                Log.d("MAIN", "" + num)
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(musicReceiver)
    }
}
