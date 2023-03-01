package com.flotroniks.apipokemonkotlin.ui.pokemonDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.databinding.FragmentPokemonDetailBinding
import com.google.gson.Gson

class PokemonDetailFragment : Fragment() {


    private lateinit var pokemon: Pokemon
    private lateinit var binding: FragmentPokemonDetailBinding
    val gson = Gson()


    companion object {
        fun newInstance(pokemon: Pokemon): PokemonDetailFragment {
            val fragment = PokemonDetailFragment()
            fragment.pokemon = pokemon
            return fragment
        }
    }
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
        //get pokemon from bundle
        pokemon = gson.fromJson(requireArguments().getString("pokemon"), Pokemon::class.java)
        binding.pokemonDetail=pokemon
        Glide.with(binding.root.context)
            .load(pokemon.image)
            .into(binding.imagePokemonDetail)

        //display pokemon type
        Glide.with(binding.root.context)
            .load(pokemon.apiTypes?.get(0)?.image)
            .into(binding.imagePokemonType1)
        //display pokemon type if it has 2 types
        if (pokemon.apiTypes?.size == 2) {
            binding.blockType2.visibility=View.VISIBLE
            Glide.with(binding.root.context)
                .load(pokemon.apiTypes?.get(1)?.image)
                .into(binding.imagePokemonType2)
        }else{
            binding.blockType2.visibility=View.GONE
        }




}
}