package com.example.lastexample

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.lastexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = "FinalExam"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // ActionBarDrawerToggle 설정
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    Toast.makeText(this, "전화번호부", Toast.LENGTH_SHORT).show()
                }
                R.id.menu2 -> {
                    Toast.makeText(this, "스톱워치", Toast.LENGTH_SHORT).show()
                }
                R.id.menu3 -> {
                    Toast.makeText(this, "일기장", Toast.LENGTH_SHORT).show()
                }
                R.id.menu4 -> {
                    Toast.makeText(this, "뷰 페이저", Toast.LENGTH_SHORT).show()
                }
            }
            binding.drawer.close()
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.drawer) { v, insets ->
            val systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }
}
