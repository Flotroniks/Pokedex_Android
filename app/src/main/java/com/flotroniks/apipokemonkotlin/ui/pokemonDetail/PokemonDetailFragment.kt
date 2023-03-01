package com.flotroniks.apipokemonkotlin.ui.pokemonDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.databinding.FragmentPokemonDetailBinding
import com.google.gson.Gson

class PokemonDetailFragment : Fragment() {

    private lateinit var binding: FragmentPokemonDetailBinding
    private lateinit var viewModel: PokemonDetailViewModel
    val gson = Gson()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().navigateUp()
        }
        //hide the action bar
        requireActivity().actionBar?.hide()

        viewModel = ViewModelProvider(this, PokemonDetailViewModelFactory())[PokemonDetailViewModel::class.java]
        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "ConfirmationFragment did not receive traveler information")
            return
        }

        val args = PokemonDetailFragmentArgs.fromBundle(bundle)
        val pokedexId= args.pokemonPokedexId
        viewModel.getPokemon(pokedexId)
        viewModel.pokemon.observe(viewLifecycleOwner) {pokemon->
            displayPokemon(pokemon)
        }


    }

    private fun displayPokemon(pokemon: Pokemon?) {
        binding.pokemonDetail=pokemon
        Glide.with(binding.root.context)
            .load(pokemon?.image)
            .into(binding.imagePokemonDetail)

        //display pokemon type
        Glide.with(binding.root.context)
            .load(pokemon?.apiTypes?.get(0)?.image)
            .into(binding.imagePokemonType1)
        //display pokemon type if it has 2 types
        if (pokemon?.apiTypes?.size == 2) {
            binding.blockType2.visibility=View.VISIBLE
            Glide.with(binding.root.context)
                .load(pokemon.apiTypes.get(1).image)
                .into(binding.imagePokemonType2)
        }else{
            binding.blockType2.visibility=View.GONE
        }
    }


}