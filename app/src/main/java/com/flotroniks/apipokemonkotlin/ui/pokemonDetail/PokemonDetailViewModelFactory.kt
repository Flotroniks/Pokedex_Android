package com.flotroniks.apipokemonkotlin.ui.pokemonDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flotroniks.apipokemonkotlin.data.repository.dao.PokemonDao
import com.flotroniks.apipokemonkotlin.ui.pokemonList.PokemonListViewModel

class PokemonDetailViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PokemonDetailViewModel() as T
        /*if (modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
            return PokemonDetailViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")*/
    }
}
