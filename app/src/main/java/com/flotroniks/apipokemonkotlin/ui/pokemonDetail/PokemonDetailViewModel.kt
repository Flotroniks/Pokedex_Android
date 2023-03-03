package com.flotroniks.apipokemonkotlin.ui.pokemonDetail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.data.models.singleton.PokemonListSingleton

class PokemonDetailViewModel : ViewModel() {
    private val pokemonListSingleton = PokemonListSingleton

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon : LiveData<Pokemon>
        get() = _pokemon

    fun getPokemon(pokedexId : Int) {
        val pokemon = pokemonListSingleton.getData().find {it.pokedexId == pokedexId}
        // get the pre evolution
        pokemon?.preEvolution =
            pokemonListSingleton.getData().find {it.name == pokemon?.preEvolution?.name}
        // get the evolution
        pokemon?.evolution = pokemonListSingleton.getData().filter {
            it.preEvolution?.name == pokemon?.name
        }.toMutableList()
        for(p in pokemon?.evolution!!) {
            println(p.name)
        }


        _pokemon.value = pokemon!!
    }

    fun isFavorite(context : Context?) : Boolean {
        return pokemon.value?.isFavorite ?: false
    }

    //add to favorite
    fun addToFavorite(context : Context?) {
        pokemon.value?.isFavorite = true
        println("add to favorite")
    }

    fun removeFromFavorite(context : Context?) {
        pokemon.value?.isFavorite = false
        println("remove from favorite")
    }
}
