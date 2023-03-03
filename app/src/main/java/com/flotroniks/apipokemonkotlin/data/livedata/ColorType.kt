package com.flotroniks.apipokemonkotlin.data.livedata

//pour chaque type de pokemon on lui associe une couleur qui accesibke via type.color
enum class ColorType(val color : Long) {
    NORMAL(0xFFA8A878),
    FIGHTING(0xFFC03028),
    FLYING(0xFFA890F0),
    POISON(0xFFA040A0),
    GROUND(0xFFE0C068),
    ROCK(0xFFB8A038),
    BUG(0xFFA8B820),
    GHOST(0xFF705898),
    STEEL(0xFFB8B8D0),
    FIRE(0xFFF08030),
    WATER(0xFF6890F0),
    GRASS(0xFF78C850),
    ELECTRIC(0xFFF8D030),
    PSYCHIC(0xFFF85888),
    ICE(0xFF98D8D8),
    DRAGON(0xFF7038F8),
    DARK(0xFF705848),
    FAIRY(0xFFEE99AC),
    UNKNOWN(0xFF68A090),
    SHADOW(0xFF000000)
}



