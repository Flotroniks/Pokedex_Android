package com.flotroniks.apipokemonkotlin.ui.pokemonLiked

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flotroniks.apipokemonkotlin.data.livedata.State
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.data.models.singleton.PokemonListSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonLikedViewModel : ViewModel() {


    private val pokemonListSingleton = PokemonListSingleton
    private val _pokemonLikedList = MutableLiveData<List<Pokemon>>()
    val pokemonLikedList : LiveData<List<Pokemon>>
        get() = _pokemonLikedList


    init {
        getPokemonLiked()

    }

    fun getPokemonLiked() {
        viewModelScope.launch {

            _pokemonLikedList.value = pokemonListSingleton.getData().filter {it.isFavorite}

        }
    }

}