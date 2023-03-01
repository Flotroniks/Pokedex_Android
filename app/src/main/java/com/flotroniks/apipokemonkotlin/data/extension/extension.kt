package com.flotroniks.apipokemonkotlin.data.extension

import android.os.Bundle
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.google.gson.Gson

val gson = Gson()
fun Bundle.putPokemon(key: String, pokemon: Pokemon) {
     //ajout du json dans le bundle
    this.putString(key, gson.toJson(pokemon).toString())
}

fun Bundle.getPokemon(key: String): Pokemon? {
    //récupération du json dans le bundle
    return gson.fromJson(this.getString(key), Pokemon::class.java)
}
