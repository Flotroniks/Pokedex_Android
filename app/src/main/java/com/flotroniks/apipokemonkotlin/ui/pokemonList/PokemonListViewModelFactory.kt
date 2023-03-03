package com.flotroniks.apipokemonkotlin.ui.pokemonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flotroniks.apipokemonkotlin.data.repository.dao.PokemonDao

class PokemonListViewModelFactory(private val pokemonDao : PokemonDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if(modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
            return PokemonListViewModel(pokemonDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
