package com.flotroniks.apipokemonkotlin.ui

import com.flotroniks.apipokemonkotlin.data.models.Pokemon

interface OnPokemonClickListener {
    fun onPokemonClick(pokemon : Pokemon)
}
