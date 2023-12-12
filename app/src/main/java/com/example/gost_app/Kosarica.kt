package com.example.gost_app
//singletone klasa dostupna u cijelom kodu koja predstavlja listu jela u kosarici
object Kosarica {
    val jelaUKosarici = mutableListOf<Jelo>()

    fun dodajJeloUKosaricu(jelo: Jelo) {
        jelaUKosarici.add(jelo)
    }

    fun ukloniJeloIzKosarice(jelo: Jelo) {
        jelaUKosarici.remove(jelo)
    }

    fun izracunajUkupnuCijenu(): Double {
        return jelaUKosarici.sumOf { it.cijena }
    }
}
