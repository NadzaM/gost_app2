package com.example.gost_app

import Korisnik
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
    //unaprijed def sharedpreferences
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedIDKorisnik: SharedPreferences
    //UNAPRIJED DEF BAZE PODATAKA - POTREBNO ZA TRAZENJE TRENUTNOG KORISNIKA
    private lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        ActivityManager.addActivity(this)
        setContentView(R.layout.activity_main)

        //Inicijalizacija SharedPreferences
                sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                sharedIDKorisnik = getSharedPreferences("userIDPrefs", Context.MODE_PRIVATE)
        //Elementi XML
        val goButton: Button = findViewById(R.id.goButton)
        val email: EditText = findViewById(R.id.editTextTextEmailAddress)
        val password: EditText = findViewById(R.id.editTextTextPassword)

        //INICIJALIZACIJA DBHELPER-a
        dbHelper = DBHelper(this, null)

        //DODAVANJE NEKIH KORISNIKA
        val nadza = Korisnik(ime = "Nadža", prezime = "Memić", rodjenje = "16.08.1998", email = "n", password = "1234", tipKorisnika = TipKorisnika.GOST)
        val alisa = Korisnik(ime = "Alisa", prezime = "Brujić", rodjenje = "04.02.1998", email = "abrujic1@etfunsa.ba", password = "1234", tipKorisnika = TipKorisnika.KUHAR)
        val treci = Korisnik(ime = "Selma", prezime = "Muhić", rodjenje = "", email = "v", password="1", tipKorisnika = TipKorisnika.GOST)

        //ubacivanje korisnika u bazu - ovo treba u register prozoru - ovdje zapravo trebe ČITANJE SA SERVERA ULOGOVANIH KORISNIKA !!!
        dodajKorisnikaUBazu(nadza)
        dodajKorisnikaUBazu(alisa)
        //INICIJALIZACIJA SHARED PREFERENCES
        //korpa
        val defaultValue = 0
        val cartValue = sharedPreferences.getInt("cartValue", defaultValue)
        if (cartValue != defaultValue) {
            val editor = sharedPreferences.edit()
            editor.putInt("cartValue", defaultValue)
            editor.commit()
        }
        //LISTENERI
        goButton.setOnClickListener {
            val unesenaLozinka = password.text.toString()
            //cuvanje trenutnog korisnika
            // Provjera inicijalizacije dbHelper-a prije pristupa bazi podataka
            if (::dbHelper.isInitialized) {
                val ulogovaniKorisnikID = dbHelper.getIDByEmail("n")
                val editor = sharedIDKorisnik.edit()
                editor.putInt("ulogovaniKorisnikID", ulogovaniKorisnikID)
                editor.apply()
            }
            val intent = Intent(this@MainActivity, JelovnikActivity::class.java)
            startActivity(intent)

        }

    }



    private fun dodajKorisnikaUBazu(korisnik: Korisnik){
        //Pozivanje metode addJelo iz DBHelpera-a
        dbHelper.addKorisnik(korisnik)

    }


}