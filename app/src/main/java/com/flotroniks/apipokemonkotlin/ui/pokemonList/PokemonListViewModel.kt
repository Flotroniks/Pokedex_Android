package com.flotroniks.apipokemonkotlin.ui.pokemonList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flotroniks.apipokemonkotlin.data.liveData.State
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.data.models.singleton.PokemonListSingleton
import com.flotroniks.apipokemonkotlin.data.repository.dao.PokemonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class PokemonListViewModel(private val repository: PokemonDao) : ViewModel() {
    private val pokemonListSingleton = PokemonListSingleton.getInstance()
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    private val _pokemonSearched = MutableLiveData<List<Pokemon>>()
    val pokemonSearched: LiveData<List<Pokemon>>
        get() = _pokemonSearched


    init {
        viewModelScope.launch {
            try {


                if (pokemonListSingleton.getData().isEmpty()) {
                    // Set the state to loading
                    _state.value = State.LOADING
                    // Get the list of pokemon from the repository asynchronously
                    val pokemonList = withContext(Dispatchers.IO) {
                        repository.getPokemonList()
                    }
                    for (pokemon in pokemonList) {
                       println(pokemon.name)
                    }
                    pokemonListSingleton.addData(pokemonList)
                    // Set the value of the live data
                    _pokemonList.value = pokemonListSingleton.getData()
                } else {
                    _pokemonList.value = PokemonListSingleton.getInstance().getData()
                }
                // Set the state to success
                _state.value = State.SUCCESS
            } catch (e: Exception) {
                e.printStackTrace()
                // Set the state to error
                _state.value = State.ERROR
                _pokemonList.value = emptyList() // Ou une liste avec un message d'erreur personnalisé
            }
        }

    }
    fun getPokemonList(): List<Pokemon> {
        return pokemonListSingleton.getData()
    }
    fun searchPokemon(pokemonName: String) {

        //cherche un pokemon par son nom ddans la base de donnée locale si il n'est pas présent dans la liste de pokemon
        _pokemonSearched.value = _pokemonList.value!!.filter {
            it.name.lowercase(Locale.getDefault())
                .startsWith(pokemonName.lowercase(Locale.getDefault()))
        }

    }
}