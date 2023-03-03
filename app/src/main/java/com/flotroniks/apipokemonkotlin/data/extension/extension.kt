package com.flotroniks.apipokemonkotlin.data.extension

import java.text.Normalizer
import java.util.*


private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

// Remove accents from a string
fun CharSequence.unaccent() : String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
}

fun CharSequence.normalize() : String {
    return this.unaccent().lowercase(Locale.ROOT)
}

