package com.flotroniks.apipokemonkotlin.ui.pokemonDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelPokemonInfo: ViewModel() {
    private val _myLiveData = MutableLiveData<String>()
    val myLiveData: LiveData<String>
        get() = _myLiveData

    fun doSomething() {
        // Perform some business logic here
        _myLiveData.value = "Hello, world!"
    }
}