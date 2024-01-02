package com.example.a3strokemanagement

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.a3strokemanagement.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//thiết lập status bar và navigation bar của máy
        window.statusBarColor= ContextCompat.getColor(this@MainActivity,R.color.Blue_Pastel)
//thiết lập action bar
        setSupportActionBar(binding.tbHust)
        supportActionBar?.title = "Welcome!"
//thiết lập bottom navigation view
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bot_nav_menu)
        bottomNavigationView.setSelectedItemId(R.id.bot_main)

        bottomNavigationView.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.bot_main -> true
                R.id.bot_records -> {
                    val animationBundle=ActivityOptions.makeCustomAnimation(this@MainActivity,R.anim.slide_in_from_left,R.anim.slide_in_from_right).toBundle()
                    startActivity(Intent(applicationContext, RecordsActivity::class.java),animationBundle)
                    finish()
                    true
                }
                R.id.bot_setting -> {
                    val animationBundle=ActivityOptions.makeCustomAnimation(this@MainActivity,R.anim.slide_in_from_left,R.anim.slide_in_from_right).toBundle()
                    startActivity(Intent(applicationContext, SettingActivity::class.java),animationBundle)
                    finish()
                    true
                }
                else->false
            }
        }

    }
}