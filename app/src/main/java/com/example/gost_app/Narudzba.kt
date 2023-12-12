package com.example.gost_app

import com.release.gfg1.DBHelper

data class Narudzba (
    val id: Int = 0,
    val idKorisnika: Int = 0,
    val idJela: Int = 0,
    val brojStola: Int = 1,
    val placeno: Boolean = false,
    val zahtjevi: String = "",
    val dostavljeno: Boolean = false,
    val spremljeno: Boolean = false,
    val zaPonijeti: Boolean = false

    )

