package com.example.lastexample

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlin.concurrent.thread

class TimerService : Service() {
    companion object {
        const val ACTION = "ACTION_COUNTER"
        const val EXTRA = "COUNTER"
        var counter = 0
        var bStarted = false
    }

    private val binder = MyBinder()
    private var serviceThread: Thread? = null

    inner class MyBinder : Binder() {
        fun stop() {
            bStarted = false
        }

        fun resume() {
            if (!bStarted) {
                bStarted = true
                runTimer()
            }
        }

        fun funB(arg: Int): Int = arg * arg
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!bStarted) {
            bStarted = true
            counter = 0
            runTimer()
        }
        return START_STICKY
    }

    private fun runTimer() {
        serviceThread = thread {
            while (bStarted) {
                Thread.sleep(1000)
                val intent = Intent(ACTION)
                intent.putExtra(EXTRA, ++counter)
                sendBroadcast(intent)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onDestroy() {
        bStarted = false
        serviceThread?.interrupt()
        super.onDestroy()
    }
}
