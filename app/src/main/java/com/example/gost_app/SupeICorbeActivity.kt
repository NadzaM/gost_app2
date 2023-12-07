package com.example.gost_app

import ActivityManager
import JeloAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.release.gfg1.DBHelper



class SupeICorbeActivity : AppCompatActivity(), OnCartUpdateListener {
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var adapter: JeloAdapter // Declaration of adapter
    lateinit var cartText: TextView // Declaration of cart TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityManager.addActivity(this)
        setContentView(R.layout.activity_supe_i_corbe)
        // Inicijalizacija SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Accessing cartText within the view setup area
        cartText = findViewById(R.id.textViewCart)

        // Other initialization code for the activity
        val back_to_menu_button: Button = findViewById(R.id.button_back_to_menu)
        val buttonX : Button = findViewById(R.id.button_x)
        back_to_menu_button.setOnClickListener {
            // Actions when the button is clicked
            val intent = Intent(this, JelovnikActivity::class.java)
            startActivity(intent)
        }
        buttonX.setOnClickListener {
            ActivityManager.finishAllExceptMain();
        }
        // Database
        val dbHelper = DBHelper(this, null)
        val jela = dbHelper.getJelaByKategorija("Supe i čorbe")

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Setting up the adapter
        val adapter = JeloAdapter(jela, this, cartText, recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val defaultValue = 0
        val cartValue = sharedPreferences.getInt("cartValue", defaultValue)
        cartText.text = cartValue.toString()


    }

    override fun updateCartValue(newValue: Int) {
        val currentTextValue = cartText.text.toString()
        val currentValue = if (currentTextValue.isNotEmpty()) currentTextValue.toInt() else 0
        var new = newValue + currentValue
        cartText.text = new.toString()
        val editor = sharedPreferences.edit()
        editor.putInt("cartValue", new)
        editor.apply()
    }
}
