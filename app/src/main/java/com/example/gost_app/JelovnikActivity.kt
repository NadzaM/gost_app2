package com.example.gost_app

import Korisnik
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.release.gfg1.DBHelper


class JelovnikActivity : AppCompatActivity() {

    //UNAPRIJED DEF SHAREDPREF I BAZE
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dbHelper: DBHelper
    private lateinit var sharedIDKorisnik: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        //POSTAVLJANJE PROZORA
        super.onCreate(savedInstanceState)
        ActivityManager.addActivity(this)
        setContentView(R.layout.activity_jelovnik)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedIDKorisnik = getSharedPreferences("userIDPrefs", Context.MODE_PRIVATE)
        val idShared = sharedIDKorisnik.getInt("ulogovaniKorisnikID", 0)


        //ELEMENTI XML-a
        val buttonX : Button = findViewById(R.id.button_x)
        val button_dorucak : ImageButton = findViewById(R.id.dorucak_button)
        val button_supe_i_corbe : ImageButton = findViewById(R.id.supe_i_corbe_button)
        val cartText : TextView = findViewById(R.id.textViewCart)

        //LISTENERI
        button_dorucak.setOnClickListener {
            val intent = Intent(this@JelovnikActivity, DorucakActivity::class.java)
            startActivity(intent)
        }

        button_supe_i_corbe.setOnClickListener {
            val intent = Intent(this@JelovnikActivity, SupeICorbeActivity::class.java)
            startActivity(intent)
        }

        buttonX.setOnClickListener {
            ActivityManager.finishAllExceptMain();
        }

        //BAZA
        //inicijalizacija DBHelper objekta
        dbHelper = DBHelper(this, null)

//DODAVANJE JELA U JELOVNIK ctr + .
        //Dodavanje Jela
        val omletPoZelji = Jelo(kategorija = "Doručak", nazivJela = "Omlet po želji", opis = "(suho meso, povrće, sir, gljive)", cijena = 7.0)
        val kajgana = Jelo(kategorija = "Doručak", nazivJela = "Kajgana", opis = "(3 jaja, salata, pecivo)", cijena = 5.0)
        val jajeNaOko = Jelo(kategorija = "Doručak", nazivJela = "Jaje na oko", opis = "(3 jaja, salata, pecivo)", cijena = 5.0)
        val kobasice = Jelo(kategorija = "Doručak", nazivJela = "Kobasice", opis = "(kobasice 150g, salata, pecivo, pomfrit)", cijena = 8.0)
        val ustipci = Jelo(kategorija = "Doručak", nazivJela = "Uštipci", opis = "(suho meso, sudžuka, kajmak, feta sir)", cijena = 12.0)
        val pohovaniSir = Jelo(kategorija = "Doručak", nazivJela = "Pohovani sir", cijena = 8.0)
        val brusketa = Jelo(kategorija = "Doručak", nazivJela = "Brusketa", opis = "(domaće pecivo, paradajz, mozzarela)", cijena = 10.0)

        val begovaCorba = Jelo(kategorija = "Supe i čorbe", nazivJela = "Begova čorba", cijena = 6.0)
        val madjarskiGulas = Jelo(kategorija = "Supe i čorbe", nazivJela = "Mađarski gulaš", cijena = 6.0)
        val kremSupaOdGljiva = Jelo(kategorija = "Supe i čorbe", nazivJela = "Krem supa od gljiva", cijena = 7.0)
        val govedjaSupa = Jelo(kategorija = "Supe i čorbe", nazivJela = "Goveđa supa", cijena = 4.0)
        val kokosijaSupa = Jelo(kategorija = "Supe i čorbe", nazivJela = "Kokošija supa", cijena = 4.0)
        val bundevaSupa = Jelo(kategorija = "Supe i čorbe", nazivJela = "Krem supa od bundeve", cijena = 6.0)
        val grah = Jelo(kategorija = "Supe i čorbe", nazivJela = "Grah", cijena = 7.0)


        dodajJeloUBazu(omletPoZelji)
        dodajJeloUBazu(kajgana)
        dodajJeloUBazu(jajeNaOko)
        dodajJeloUBazu(kobasice)
        dodajJeloUBazu(ustipci)
        dodajJeloUBazu(pohovaniSir)
        dodajJeloUBazu(brusketa)

        dodajJeloUBazu(begovaCorba)
        dodajJeloUBazu(madjarskiGulas)
        dodajJeloUBazu(kremSupaOdGljiva)
        dodajJeloUBazu(govedjaSupa)
        dodajJeloUBazu(kokosijaSupa)
        dodajJeloUBazu(bundevaSupa)
        dodajJeloUBazu(grah)


        val defaultValue = 0


        val cartValue = sharedPreferences.getInt("cartValue", defaultValue)
        cartText.text = cartValue.toString()


    }


    private fun dodajJeloUBazu(jelo: Jelo){
        //Pozivanje metode addJelo iz DBHelpera-a
        dbHelper.addJelo(jelo.kategorija, jelo.nazivJela, jelo.opis, jelo.cijena)

    }

}