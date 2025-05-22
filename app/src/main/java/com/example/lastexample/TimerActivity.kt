package com.example.lastexample

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lastexample.databinding.ActivityTimerBinding
import java.util.Timer

class TimerActivity : AppCompatActivity() {

    lateinit var binding: ActivityTimerBinding
    lateinit var serviceBinder: TimerService.MyBinder
    private var isBound = false
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            serviceBinder = p1 as TimerService.MyBinder
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }
    }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            if (intent?.action == TimerService.ACTION) {
                binding.display.text = intent?.getIntExtra(TimerService.EXTRA, 0).toString()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        var isPaused = false

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = "스톱워치"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val serviceIntent = Intent(this, TimerService::class.java)
        startService(serviceIntent)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)

        registerReceiver(receiver, IntentFilter(TimerService.ACTION), RECEIVER_EXPORTED)

        // START/RESTART 버튼
        binding.startBtn.setOnClickListener {
            if (isPaused) {
                serviceBinder.resume()
                binding.startBtn.text = "PAUSE"
                isPaused = false
            }
        }

        // PAUSE 버튼
        binding.pauseBtn.setOnClickListener {
            if (isBound) {
                serviceBinder.stop()
                binding.startBtn.text = "RESTART"
                isPaused = true
            }
        }
//        binding.startBtn.setOnClickListener {
//            if (isPause) {
//                binding.startBtn.text = "RESTART"
//            } else {
//                binding.startBtn.text = "START"
//            }
//            startService(Intent(this, TimerService::class.java))
//        }

//        binding.pauseBtn.setOnClickListener {
//            serviceBinder.stop()
//            isPause = true
//            binding.startBtn.text = "RESTART"
//        }

    }
    override fun onDestroy() {
        if (isBound) unbindService(connection)
//        unregisterReceiver(receiver)
        super.onDestroy()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}