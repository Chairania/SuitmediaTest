package com.suitmedia.assignment.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.suitmedia.assignment.R

class FirstScreen : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPalindrome: EditText
    private lateinit var btnCheck: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        setContentView(R.layout.activity_first_screen)

        etName = findViewById(R.id.etName)
        etPalindrome = findViewById(R.id.etPalindrome)
        btnCheck = findViewById(R.id.btnCheck)
        btnNext = findViewById(R.id.btnNext)

        btnCheck.setOnClickListener {
            val text = etPalindrome.text.toString()
            if (text.isBlank()) {
                showDialog("Please enter a sentence to check.")
                return@setOnClickListener
            }
            val isPalindrome = checkPalindrome(text)
            showDialog(if (isPalindrome) "isPalindrome" else "not palindrome")
        }

        btnNext.setOnClickListener {
            val name = etName.text.toString()
            if (name.isBlank()) {
                showDialog("Please enter your name.")
                return@setOnClickListener
            }

            val intent = Intent(this, SecondScreen::class.java)
            intent.putExtra("user_name", name)
            startActivity(intent)
        }
    }


    private fun checkPalindrome(text: String): Boolean {
        val cleaned = text.replace(" ", "").lowercase()
        return cleaned == cleaned.reversed()
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Result")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
