package com.flotroniks.apipokemonkotlin.ui.loading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flotroniks.apipokemonkotlin.data.livedata.State
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.data.models.singleton.PokemonListSingleton
import com.flotroniks.apipokemonkotlin.data.repository.dao.PokemonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoadingViewModel(private val repository : PokemonDao) : ViewModel() {

    private val pokemonListSingleton = PokemonListSingleton
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList : LiveData<List<Pokemon>>
        get() = _pokemonList
    private val _state = MutableLiveData<State>()
    val state : LiveData<State>
        get() = _state

    // add a new MutableLiveData to handle the end of the animation
    private val _animationEnded = MutableLiveData<Boolean>()
    val animationEnded : LiveData<Boolean>
        get() = _animationEnded

    init {
        _animationEnded.value = false
        getPokemonList()
    }

    fun animationEnded() {
        viewModelScope.launch {_animationEnded.value = true}
    }

    fun getPokemonList() {
        viewModelScope.launch {
            try {

                _state.value = State.LOADING
                if(pokemonListSingleton.getData().isEmpty()) {
                    // Set the state to loading

                    // Get the list of pokemon from the repository asynchronously
                    val pokemonList = withContext(Dispatchers.IO) {repository.getPokemonList()}
                    // Add the list to the singleton
                    pokemonListSingleton.addData(pokemonList)
                    // Set the value of the live data
                    _pokemonList.value = pokemonListSingleton.getData()
                } else _pokemonList.value = pokemonListSingleton.getData()

                _state.value = State.SUCCESS
            } catch(e : Exception) {
                _state.value = State.ERROR
                e.printStackTrace()

                _pokemonList.value =
                    emptyList() // Ou une liste avec un message d'erreur personnalisé
            }
        }
    }
}
