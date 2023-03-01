package com.flotroniks.apipokemonkotlin.data.repository.dao

import com.flotroniks.apipokemonkotlin.data.models.*
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import org.json.JSONObject
import java.net.URL

class PokemonDaoImpl: PokemonDao {
    val gson = Gson()

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

   /* override suspend fun getPokemonDetails(id: Int): List<Pokemon> {
        val jsonStr = "{\"id\":678,\"pokedexId\":678,\"name\":\"Mistigrix\",\"image\":\"https:\\/\\/raw.githubusercontent.com\\/PokeAPI\\/sprites\\/master\\/sprites\\/pokemon\\/other\\/official-artwork\\/678.png\",\"sprite\":\"https:\\/\\/raw.githubusercontent.com\\/PokeAPI\\/sprites\\/master\\/sprites\\/pokemon\\/678.png\",\"slug\":\"Mistigrix\",\"stats\":{\"HP\":74,\"attack\":48,\"defense\":76,\"special_attack\":83,\"special_defense\":81,\"speed\":104},\"apiTypes\":[{\"name\":\"Psy\",\"image\":\"https:\\/\\/static.wikia.nocookie.net\\/pokemongo\\/images\\/2\\/21\\/Psychic.png\"}],\"apiGeneration\":6,\"apiResistances\":[{\"name\":\"Normal\",\"damage_multiplier\":1,\"damage_relation\":\"neutral\"},{\"name\":\"Combat\",\"damage_multiplier\":0.5,\"damage_relation\":\"resistant\"},{\"name\":\"Vol\",\"damage_multiplier\":1,\"damage_relation\":\"neutral\"},{\"name\":\"Poison\",\"damage_multiplier\":1,\"damage_relation\":\"neutral\"},{\"name\":\"Sol\",\"damage_multiplier\":1,\"damage_relation\":\"neutral\"},{\"name\":\"Roche\",\"damage_multiplier\":1,\"damage_relation\":\"neutral\"},{\"name\":\"Insecte\",\"damage_multiplier\":2,\"damage_relation\":\"vulnerable\"},{\"name\":\"Spectre\",\"damage_multiplier\":2,\"damage_relation\":\"vulnerable\"},{\"name\":\"Acier\",\"damage_multiplier\":1,\"damage_relation\":\"neutral\"},{\"name\":\"Feu\",\"damage_multiplier\":1,\"damage_relation\":\"neutral\"},{\"name\":\"Eau\",\"damage_multiplier\":1,\"damage_relation\":\"neutral\"},{\"name\":\"Plante\",\"damage_multiplier\":1,\"damage_relation\":\"neutral\"},{\"name\":\"\\u00c9lectrik\",\"damage_multiplier\":1,\"damage_relation\":\"neutral\"},{\"name\":\"Psy\",\"damage_multiplier\":0.5,\"damage_relation\":\"resistant\"},{\"name\":\"Glace\",\"damage_multiplier\":1,\"damage_relation\":\"neutral\"},{\"name\":\"Dragon\",\"damage_multiplier\":1,\"damage_relation\":\"neutral\"},{\"name\":\"T\\u00e9n\\u00e8bres\",\"damage_multiplier\":2,\"damage_relation\":\"vulnerable\"},{\"name\":\"F\\u00e9e\",\"damage_multiplier\":1,\"damage_relation\":\"neutral\"}],\"resistanceModifyingAbilitiesForApi\":[],\"apiEvolutions\":[],\"apiPreEvolution\":{\"name\":\"Psystigri\",\"pokedexIdd\":677},\"apiResistancesWithAbilities\":[]}"
        val json = JSONObject(jsonStr)
        val pokemon = gson.fromJson(json.toString(), Pokemon::class.java)
        return listOf(pokemon)
    }*/

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