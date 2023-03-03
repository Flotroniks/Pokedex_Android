package com.flotroniks.apipokemonkotlin.ui.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flotroniks.apipokemonkotlin.data.repository.dao.PokemonDao

class LoadingViewModelFactory(private val pokemonDao : PokemonDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {

        if(modelClass.isAssignableFrom(LoadingViewModel::class.java)) {
            return LoadingViewModel(pokemonDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
