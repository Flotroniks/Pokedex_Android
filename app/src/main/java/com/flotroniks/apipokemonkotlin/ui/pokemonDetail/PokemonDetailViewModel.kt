package com.flotroniks.apipokemonkotlin.ui.pokemonDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.data.models.singleton.PokemonListSingleton
import com.flotroniks.apipokemonkotlin.data.repository.dao.PokemonDao

class PokemonDetailViewModel() : ViewModel() {
    private val pokemonListSingleton = PokemonListSingleton.getInstance()

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    fun getPokemon(pokedexId: Int){
        _pokemon.value = pokemonListSingleton.getData().find { it.pokedexId == pokedexId }
    }


}