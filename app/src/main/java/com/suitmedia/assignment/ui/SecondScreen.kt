package com.suitmedia.assignment.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.bumptech.glide.Glide
import com.suitmedia.assignment.R

class SecondScreen : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_SELECT_USER = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)
        window.statusBarColor = Color.WHITE
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        val ivBack = findViewById<ImageButton>(R.id.ivBack)
        ivBack.setOnClickListener {
            finish()
        }

        val name = intent.getStringExtra("user_name") ?: "User"
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvSelectedUser = findViewById<TextView>(R.id.tvSelectedUser)
        val btnChooseUser = findViewById<Button>(R.id.btnChooseUser)

        tvName.text = name
        tvSelectedUser.text = "Selected User Name"

        btnChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdScreen::class.java)
            intent.putExtra("user_name", name)
            startActivityForResult(intent, REQUEST_CODE_SELECT_USER)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_USER && resultCode == RESULT_OK) {
            val selectedUserName = data?.getStringExtra("selected_user_name")
            val selectedUserAvatar = data?.getStringExtra("selected_user_avatar")

            selectedUserName?.let {
                findViewById<TextView>(R.id.tvSelectedUser).text = it
            }
            selectedUserAvatar?.let { url ->
                val imageView = findViewById<ImageView>(R.id.ivUserAvatar)
                Glide.with(this).load(url).into(imageView)
            }
        }
    }


}
