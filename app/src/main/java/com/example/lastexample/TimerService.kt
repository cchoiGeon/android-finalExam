package com.example.lastexample

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlin.concurrent.thread

class TimerService :Service() {
    private var time = 0
    private var isRunning = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isRunning) {
            isRunning = true
            thread {
                while (isRunning) {
                    Thread.sleep(1000)
                    time++
                    Log.d("test123","$time")
                    val broadcast = Intent("ACTION_TIMER")
                    broadcast.putExtra("time", time)
                    sendBroadcast(broadcast)
                }
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        isRunning = false
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}