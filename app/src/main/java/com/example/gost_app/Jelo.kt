package com.example.gost_app

data class Jelo (
    val id : Int = 0,
    val kategorija : String,
    val nazivJela : String,
    val opis : String = "",
    val cijena: Double,
    val stol : Int = 101,
    val zaPonijeti: Boolean = false,
    val zahtjevi: String = ""

)

