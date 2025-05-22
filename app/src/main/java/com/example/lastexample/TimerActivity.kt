package com.example.lastexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lastexample.databinding.ActivityTimerBinding

class TimerActivity : AppCompatActivity() {

    lateinit var binding: ActivityTimerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.main)
        setSupportActionBar(binding.toolbar)
        title = "스톱워치"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val time = intent?.getIntExtra("time", 0) ?: 0
                binding.display.text = "$time 초"
            }
        }

        // 브로드캐스트 등록
        val filter = IntentFilter("ACTION_TIMER")
        registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)


        // START 버튼 클릭 시 서비스 시작
        binding.startBtn.setOnClickListener {
            startService(Intent(this, TimerService::class.java))
        }

//        unregisterReceiver(receiver)
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