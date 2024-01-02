package com.example.a3strokemanagement

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.a3strokemanagement.databinding.ActivitySettingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

private lateinit var binding: ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
//thiết lập status bar và navigation bar của máy
        window.statusBarColor=ContextCompat.getColor(this@SettingActivity,R.color.Red_Pastel)
//thiết lập action bar
        setSupportActionBar(binding.tbHust)
        supportActionBar?.title = "Setting"
//thiết lập bottom navigation view
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bot_nav_menu)
        bottomNavigationView.setSelectedItemId(R.id.bot_setting)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bot_setting -> true
                R.id.bot_records -> {
                    val animationBundle= ActivityOptions.makeCustomAnimation(this@SettingActivity,R.anim.slide_left,R.anim.slide_right).toBundle()
                    startActivity(Intent(applicationContext, RecordsActivity::class.java),animationBundle)
                    finish()
                    true
                }

                R.id.bot_main -> {
                    val animationBundle= ActivityOptions.makeCustomAnimation(this@SettingActivity,R.anim.slide_left,R.anim.slide_right).toBundle()
                    startActivity(Intent(applicationContext, MainActivity::class.java),animationBundle)
                    finish()
                    true
                }

                else -> false
            }
        }
        auth = FirebaseAuth.getInstance()
        binding.btnSignOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, SignInPage::class.java)
            startActivity(intent)
        }
    }
}