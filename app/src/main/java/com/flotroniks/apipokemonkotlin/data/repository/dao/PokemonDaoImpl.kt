package com.flotroniks.apipokemonkotlin.data.repository.dao

import com.flotroniks.apipokemonkotlin.data.extension.normalize
import com.flotroniks.apipokemonkotlin.data.models.*
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import java.net.URL

class PokemonDaoImpl : PokemonDao {
    private val gson = Gson()

    override suspend fun getPokemonList() : List<Pokemon> {
        // fetch data from api and return it
        val apiUrl = "https://pokebuildapi.fr/api/v1/pokemon"
        println("apiUrl: $apiUrl")
        val response = URL(apiUrl).readText()
        val arrayOfPokemons = gson.fromJson(response, Array<Pokemon>::class.java)

        for(pokemon in arrayOfPokemons) {

            if(pokemon.apiPreEvolution is LinkedTreeMap<*, *>) {

                val map = pokemon.apiPreEvolution as LinkedTreeMap<String, Double>
                val name = map["name"].toString()
                val pokedexId = map["pokedexId"]
                if(pokemon.name == "Pikachu") pokemon.preEvolution = Pokemon(name = "Pichu")
                else pokemon.preEvolution = Pokemon(name = name, pokedexId = pokedexId?.toInt())
            }
            if(pokemon.name.normalize() == "pikachu") {
                pokemon.preEvolution = Pokemon(name = "Pichu")
            }
            if(pokemon.name.normalize() == "pichu") {
                pokemon.evolution = listOf<Pokemon>(Pokemon(name = "Pikachu")).toMutableList()
            }
            // TODO: resistanceModifyingAbilitiesForApi


        }

        return arrayOfPokemons.toList()
    }

    override suspend fun getPokemonByName(pokemonName : String) : Pokemon? {
        val apiUrl = "https://pokebuildapi.fr/api/v1/pokemon/$pokemonName"
        try {
            val response = URL(apiUrl).readText()
            println("apiUrl: $apiUrl")
            val pokemon = gson.fromJson(response, Pokemon::class.java)

            if(pokemon.apiPreEvolution is LinkedTreeMap<*, *>) {

                val map = pokemon.apiPreEvolution as LinkedTreeMap<String, Double>
                val name = map["name"].toString()
                val pokedexId = map["pokedexId"]
                pokemon.preEvolution = Pokemon(name = name, pokedexId = pokedexId?.toInt())
            }
            // TODO: resistanceModifyingAbilitiesForApi
            return pokemon
        } catch(e : Exception) {
            e.printStackTrace()
            return null
        }
    }
}
