package com.example.papa.serviceandui

import android.app.Service
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.os.Message
import java.io.FileDescriptor
import java.io.IOException

class MusicService: Service(){
    private val music = "NguoiAmPhu.mp3"
    private var state: Int = 0x111
    private lateinit var manager: AssetManager
    private lateinit var player: MediaPlayer
    override fun onBind(p0: Intent?): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val type: Int = intent!!.getIntExtra("type", 0)
        when (type){
            1 ->
            {
                if (state == 0x111){
                    startMusic()
                    state = 0x112
                }
                else if (state == 0x112){
                    player.pause()
                    state = 0x113
                }
                else if (state == 0x113){
                    player.start()
                    state = 0x113
                }
            }
            2 ->{
                if (state == 0x112 || state == 0x113){
                    player.stop()
                    state = 0x111

                }
            }
        }
        val resultIntent = Intent(MainActivity.ACION_MAIN)
        resultIntent.putExtra("num", 1)
        resultIntent.putExtra("state", state)
        sendBroadcast(resultIntent)
        return START_REDELIVER_INTENT

    }

    override fun onCreate() {
        super.onCreate()
        manager = assets
        player = MediaPlayer()
        player.setOnCompletionListener {
            startMusic()
        }
        val intent = Intent(MainActivity.ACION_MAIN)
        sendBroadcast(intent)
    }

    fun startMusic(){
        try {
            val afd: AssetFileDescriptor = manager.openFd(music)
            player.reset()
            player.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            player.prepare()
            player.start()
        }catch (e:IOException){
            e.printStackTrace()
        }
    }
}