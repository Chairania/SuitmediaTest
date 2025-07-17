package com.suitmedia.assignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import androidx.core.view.WindowInsetsControllerCompat
import com.suitmedia.assignment.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = Color.parseColor("#2B637B")
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, FirstScreen::class.java))
            finish()
        }, 2000)
    }

}