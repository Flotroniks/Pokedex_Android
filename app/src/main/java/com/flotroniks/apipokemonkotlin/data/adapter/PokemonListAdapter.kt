package com.flotroniks.apipokemonkotlin.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.databinding.ItemPokemonBinding
import com.flotroniks.apipokemonkotlin.ui.OnPokemonClickListener


class PokemonListAdapter(private var pokemonList: List<Pokemon>,
                         private val listener: OnPokemonClickListener
) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        //convert url to image and set it to imageview
        Glide.with(holder.itemView.context)
            .load(pokemon.image)
            .into(holder.binding.imagePokemon)
        //set pokemon name
        holder.binding.pokemon = pokemon
        holder.itemView.setOnClickListener {
            // TODO: Ouvrir le fragment détaillé pour le Pokemon sélectionné
            println("pokemon: ${pokemon.name}")
            listener.onPokemonClick(pokemon)

        }
    }



    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun updatePokemonList(pokemonList: List<Pokemon>?) {
        pokemonList?.let {
            this.pokemonList = it
            notifyDataSetChanged()
        }

    }
}
