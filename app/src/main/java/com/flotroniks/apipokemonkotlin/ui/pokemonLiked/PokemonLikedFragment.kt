package com.flotroniks.apipokemonkotlin.ui.pokemonLiked

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.flotroniks.apipokemonkotlin.data.adapter.PokemonLikedListAdapter
import com.flotroniks.apipokemonkotlin.data.adapter.PokemonListAdapter
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.data.models.singleton.PokemonListSingleton

import com.flotroniks.apipokemonkotlin.databinding.FragmentPokemonLikedBinding
import com.flotroniks.apipokemonkotlin.ui.OnPokemonClickListener
import com.flotroniks.apipokemonkotlin.ui.loading.LoadingFragmentDirections
import com.flotroniks.apipokemonkotlin.ui.loading.LoadingViewModel
import com.google.android.flexbox.*

//make a fragment for the liked pokemon
class PokemonLikedFragment : Fragment(), OnPokemonClickListener {

    private lateinit var viewModel : PokemonLikedViewModel
    private lateinit var binding : FragmentPokemonLikedBinding
    private lateinit var pokemonLikedListAdapter : PokemonLikedListAdapter

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        binding = FragmentPokemonLikedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PokemonLikedViewModel::class.java)
        viewModel.getPokemonLiked()

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.alignItems = AlignItems.STRETCH
        layoutManager.justifyContent = JustifyContent.SPACE_EVENLY

        binding.layoutManager = layoutManager
        val pokemonList = viewModel.pokemonLikedList.value ?: emptyList()
        for(pokemon in pokemonList) {
            println(pokemon.name)
        }
        pokemonLikedListAdapter = PokemonLikedListAdapter(pokemonList, this)
        binding.adapter = pokemonLikedListAdapter

        viewModel.pokemonLikedList.observe(viewLifecycleOwner) {list ->
            pokemonLikedListAdapter = PokemonLikedListAdapter(list, this)


        }

    }

    override fun onPokemonClick(pokemon : Pokemon) {
        //navigate to the pokemon detail fragment
        val action =
            PokemonLikedFragmentDirections.actionPokemonLikedFragmentToPokemonDetailFragment(pokemon.pokedexId!!)
        findNavController().navigate(action)
    }
}
