package com.release.gfg1

import Korisnik
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.gost_app.Jelo
import com.example.gost_app.Narudzba

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, RESTORAN, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + JELOVNIK + " ("
                + ID_JELO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KATEGORIJA + " TEXT,"
                + NAZIV_JELA + " TEXT, "
                + OPIS + " TEXT, "
                + CIJENA + " REAL" + ")")

        db.execSQL(query)
        val query2: String = ("CREATE TABLE " + KORISNICI + " ("
                + ID_KORISNIK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + IME + " TEXT,"
                + PREZIME + " TEXT,"
                + RODJENJE + " TEXT,"
                + EMAIL + " TEXT,"
                + PASSWORD + " TEXT,"
                + TIPKORISNIKA + " TEXT" + ")")
        db.execSQL(query2)

        val query3: String = ("CREATE TABLE " + NARUDZBA + " ("
                + ID_NARUDZBA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_KORISNIKA + " INTEGER,"
                + ID_JELA + " INTEGER,"
                + BROJ_STOLA + " INTEGER,"
                + PLACENO + " INTEGER,"
                + ZAHTJEVI + " TEXT,"
                + DOSTAVLJENO + " INTEGER,"
                + SPREMLJENO + " INTEGER,"
                + ZA_PONIJETI + " INTEGER,"
                + "FOREIGN KEY (" + ID_KORISNIKA + ") REFERENCES " + KORISNICI + "(" + ID_KORISNIK + "),"
                + "FOREIGN KEY (" + ID_JELA + ") REFERENCES " + JELOVNIK + "(" + ID_JELO + ")"
                + ")")
        db.execSQL(query3)


    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS $JELOVNIK")
        db.execSQL("DROP TABLE IF EXISTS $KORISNICI")
        db.execSQL("DROP TABLE IF EXISTS $NARUDZBA")
        onCreate(db)
    }


    fun addJelo(kategorija: String, naziv_jela: String, opis: String, cijena: Double) {
        val values = ContentValues()
        values.put(KATEGORIJA, kategorija)
        values.put(NAZIV_JELA, naziv_jela)
        values.put(CIJENA, cijena)
        values.put(OPIS, opis)

        val db = this.writableDatabase
        db.insert(JELOVNIK, null, values)
        db.close()
    }
    fun addKorisnik(korisnik: Korisnik){
        val values = ContentValues()
        values.put(IME, korisnik.ime)
        values.put(PREZIME, korisnik.prezime)
        values.put(RODJENJE, korisnik.rodjenje)
        values.put(EMAIL, korisnik.email)
        values.put(PASSWORD, korisnik.password)
        values.put(TIPKORISNIKA, korisnik.tipKorisnika.toString())

        val db = this.writableDatabase
        db.insert(KORISNICI, null, values)
        db.close()
    }

    fun addNarudzba(narudzba: Narudzba){
        val values = ContentValues()
        values.put(ID_KORISNIKA, narudzba.idKorisnika)
        values.put(ID_JELA, narudzba.idJela)
        values.put(BROJ_STOLA, narudzba.brojStola)
        values.put(PLACENO, narudzba.placeno)
        values.put(ZAHTJEVI, narudzba.zahtjevi)
        values.put(DOSTAVLJENO, narudzba.dostavljeno)
        values.put(SPREMLJENO, narudzba.spremljeno)
        values.put(ZA_PONIJETI, narudzba.zaPonijeti)
        val db = this.writableDatabase
        db.insert(NARUDZBA, null, values)
        db.close()
    }
    fun addJelo(jelo: Jelo) {
        val values = ContentValues()
        values.put(KATEGORIJA, jelo.kategorija)
        values.put(NAZIV_JELA, jelo.nazivJela)
        values.put(CIJENA, jelo.cijena)
        values.put(OPIS, jelo.opis)
        val db = this.writableDatabase
        db.insert(JELOVNIK, null, values)
        db.close()
    }


    fun getName(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + JELOVNIK, null)

    }
//ELEMENTI BAZE
    companion object{
        private val RESTORAN = "RESTORAN" //IME BAZE PODATAKA
        private val DATABASE_VERSION = 1
        val JELOVNIK = "jelovnik" //TABELA 1
        val ID_JELO = "id_jelo"
        val KATEGORIJA = "kategorija"
        val NAZIV_JELA = "naziv_jela"
        val OPIS = "opis"
        val CIJENA = "cijena"

        val KORISNICI = "korisnici" //TABELA 2
        val ID_KORISNIK = "id_korisnik"
        val IME = "ime"
        val PREZIME = "prezime"
        val RODJENJE = "rodjenje"
        val EMAIL = "email"
        val PASSWORD = "password"
        val TIPKORISNIKA = "tipkorisnika"

        val NARUDZBA = "narudzba" //TABELA 3
        val ID_NARUDZBA = "id_narudzba" //brojac narudzbi univerzalni :D
        val BROJ_STOLA = "broj_stola"
        val PLACENO = "placeno"
        val ZAHTJEVI = "zahtjevi"
        val DOSTAVLJENO = "dostavljeno"
        val SPREMLJENO = "spremljeno"
        val ZA_PONIJETI = "za_ponijeti"
        val ID_KORISNIKA = "id_korisnika"
        val ID_JELA = "id_jela"
    }


    fun getJelaByKategorija(kategorija: String): ArrayList<Jelo> {
        val jelaList = ArrayList<Jelo>()
        val db = this.readableDatabase

        // SQL upit za dohvaćanje jela s određenom kategorijom
        val query = "SELECT * FROM $JELOVNIK WHERE $KATEGORIJA = ?"
        val cursor = db.rawQuery(query, arrayOf(kategorija))
        // Iteriranje kroz rezultat upita i stvaranje liste jela
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val jelo = Jelo(
                    cursor.getInt(cursor.getColumnIndex(ID_JELO)),
                    cursor.getString(cursor.getColumnIndex(KATEGORIJA)),
                    cursor.getString(cursor.getColumnIndex(NAZIV_JELA)),
                    cursor.getString(cursor.getColumnIndex(OPIS)),
                    cursor.getDouble(cursor.getColumnIndex(CIJENA))
                    // Dodajte sve ostale kolone koje trebate
                )
                jelaList.add(jelo)
            } while (cursor.moveToNext())
        }

        cursor?.close()
        db.close()

        return jelaList
    }

    fun getIDByEmail(email: String): Int {
        val db = this.readableDatabase
        val query = "SELECT $ID_KORISNIK FROM $KORISNICI WHERE $EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        var userID = -1 // Postavljamo na nevaljani ID kao podrazumijevanu vrijednost

        cursor.use {
            if (it.moveToFirst()) {
                userID = it.getInt(it.getColumnIndex(ID_KORISNIK))
                Log.d("UserID", "Pronađen ID korisnika: $userID")
            }else{
                Log.d("UserID", "Nije pronađen ID korisnika za e-mail: $email")
            }
        }

        return userID
    }




}
