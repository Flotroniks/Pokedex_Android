package com.flotroniks.apipokemonkotlin.ui.pokemonDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PokemonDetailViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        return PokemonDetailViewModel() as T

    }
}
