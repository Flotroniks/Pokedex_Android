package com.flotroniks.apipokemonkotlin.data.repository.dao

import com.flotroniks.apipokemonkotlin.data.models.Pokemon

interface PokemonDao {

    suspend fun getPokemonList() : List<Pokemon>

    /*suspend fun getPokemonDetails(id: Int): List<Pokemon>*/
    suspend fun getPokemonByName(pokemonName : String) : Pokemon?
}
