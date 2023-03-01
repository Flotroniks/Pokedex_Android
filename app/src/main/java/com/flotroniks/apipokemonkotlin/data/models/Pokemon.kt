package com.flotroniks.apipokemonkotlin.data.models

data class Pokemon(
    val apiEvolutions: List<ApiEvolution>?=null,
    val apiGeneration: Int?=null,
    val apiPreEvolution: Any?=null,
    val apiResistances: List<ApiResistance>?=null,
    val apiResistancesWithAbilities: List<Any>?=null,
    val apiTypes: List<ApiType>?=null,
    val id: Int,
    val image: String?=null,
    val name: String,
    val pokedexId: Int?=null,
    val resistanceModifyingAbilitiesForApi: Any?=null,
    val slug: String?=null,
    val sprite: String?=null,
    val stats: Stats?=null,

) {
    var preEvolution : PokemonPreEvolution? = null
    var pokemonPreEvolution : Pokemon? = null
    var evolution : List<Pokemon>? = null
}

