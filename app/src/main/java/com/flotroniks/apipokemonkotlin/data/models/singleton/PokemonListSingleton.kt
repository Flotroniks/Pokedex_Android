package com.flotroniks.apipokemonkotlin.data.models.singleton

import com.flotroniks.apipokemonkotlin.data.models.Pokemon

object PokemonListSingleton {
    private var myData = listOf<Pokemon>()

    fun addData(data : List<Pokemon>) {
        myData = data
    }

    fun getData() : List<Pokemon> {
        return myData
    }
}
