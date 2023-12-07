package com.example.gost_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.release.gfg1.DBHelper


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        ActivityManager.addActivity(this)
        setContentView(R.layout.activity_main)
        //Inicijalizacija SharedPreferences
                sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val goButton: Button = findViewById(R.id.goButton)
        val email: EditText = findViewById(R.id.editTextTextEmailAddress)
        val password: EditText = findViewById(R.id.editTextTextPassword)

        goButton.setOnClickListener {
            val unesenaLozinka = password.text.toString()
            val intent = Intent(this@MainActivity, JelovnikActivity::class.java)
            startActivity(intent)

        }
        // Postavljanje početne vrijednosti kad se aplikacija prvi put pokrene
        val defaultValue = 0
        val cartValue = sharedPreferences.getInt("cartValue", defaultValue)
        if (cartValue != defaultValue) {
            val editor = sharedPreferences.edit()
            editor.putInt("cartValue", defaultValue)
            editor.apply()
        }
    }
}