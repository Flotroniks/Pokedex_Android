package com.flotroniks.apipokemonkotlin.data.repository.dao

import com.flotroniks.apipokemonkotlin.data.models.*
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import org.json.JSONObject
import java.net.URL

class PokemonDaoImpl: PokemonDao {
    private val gson = Gson()

    override suspend fun getPokemonList(): List<Pokemon> {
        //fetch data from api and return it
        val apiUrl = "https://pokebuildapi.fr/api/v1/pokemon"
        val response = URL(apiUrl).readText()
        val arrayOfPokemons =gson.fromJson(response, Array<Pokemon>::class.java)

        for (pokemon in arrayOfPokemons) {

            if(pokemon.apiPreEvolution is LinkedTreeMap<*, *>) {

                val map = pokemon.apiPreEvolution as LinkedTreeMap<String, Double>
                val name = map["name"].toString()
                val pokedexId = map["pokedexId"]
                pokemon.preEvolution = PokemonPreEvolution(name, pokedexId)
            }
            //TODO: resistanceModifyingAbilitiesForApi


        }

        return arrayOfPokemons.toList()

    }


    override suspend fun getPokemonByName(pokemonName: String): Pokemon? {
        val apiUrl = "https://pokebuildapi.fr/api/v1/pokemon/$pokemonName"
        try {
            val response = URL(apiUrl).readText()
            val pokemon =gson.fromJson(response, Pokemon::class.java)

            if(pokemon.apiPreEvolution is LinkedTreeMap<*, *>) {

                val map = pokemon.apiPreEvolution as LinkedTreeMap<String, Double>
                val name = map["name"].toString()
                val pokedexId = map["pokedexId"]
                pokemon.preEvolution = PokemonPreEvolution(name, pokedexId)
            }
            //TODO: resistanceModifyingAbilitiesForApi
            return pokemon
        }catch (e: Exception) {
            e.printStackTrace()
            return null
        }



    }
}