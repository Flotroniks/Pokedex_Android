package com.flotroniks.apipokemonkotlin.data.models.singleton

import com.flotroniks.apipokemonkotlin.data.models.Pokemon

class PokemonListSingleton private constructor() {
    private var myData = listOf<Pokemon>()

    companion object {
        @Volatile
        private var instance: PokemonListSingleton? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: PokemonListSingleton().also { instance = it }
            }
    }

    fun addData(data:List<Pokemon> ) {
        myData = data
    }

    fun getData(): List<Pokemon>{
        return myData
    }
}