package com.flotroniks.apipokemonkotlin.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.databinding.ItemPokemonBinding
import com.flotroniks.apipokemonkotlin.databinding.ItemPokemonEvolutionBinding
import com.flotroniks.apipokemonkotlin.ui.OnPokemonClickListener
import com.flotroniks.apipokemonkotlin.ui.pokemonDetail.PokemonDetailFragment

class PokemonEvolutionListAdapter(
    private var pokemonList : List<Pokemon>,
    private val listener : OnPokemonClickListener,
) : RecyclerView.Adapter<PokemonEvolutionListAdapter.ViewHolder>() {

    class ViewHolder(val binding : ItemPokemonEvolutionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonEvolutionBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        val pokemon = pokemonList[position]
        Glide.with(holder.itemView.context).load(pokemon.image).into(holder.binding.imagePokemon)
        holder.binding.pokemon = pokemon
        holder.binding.pokemonItemLinearLayout.setOnClickListener {
            listener.onPokemonClick(pokemon)
        }


    }

    override fun getItemCount() : Int {
        return pokemonList.size
    }

    fun updatePokemonList(pokemonList : List<Pokemon>?) {
        pokemonList?.let {
            this.pokemonList = it
            notifyDataSetChanged()
        }
    }
}
