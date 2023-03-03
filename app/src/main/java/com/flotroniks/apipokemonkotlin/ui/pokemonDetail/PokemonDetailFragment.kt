package com.flotroniks.apipokemonkotlin.ui.pokemonDetail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.flotroniks.apipokemonkotlin.R
import com.flotroniks.apipokemonkotlin.data.adapter.PokemonEvolutionListAdapter
import com.flotroniks.apipokemonkotlin.data.extension.normalize
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.databinding.FragmentPokemonDetailBinding
import com.flotroniks.apipokemonkotlin.ui.OnPokemonClickListener
import com.google.android.flexbox.*
import com.google.android.material.transition.MaterialContainerTransform
import com.google.gson.Gson

class PokemonDetailFragment : Fragment(), OnPokemonClickListener {

    private lateinit var pokemonEvolutionListAdapter : PokemonEvolutionListAdapter
    private lateinit var binding : FragmentPokemonDetailBinding
    private lateinit var viewModel : PokemonDetailViewModel
    val gson = Gson()

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_pokemonDetailFragment_to_pokemonListFragment)
        }
        // hide the action bar
        requireActivity().actionBar?.hide()
        val sharedElement = binding.imagePokemonDetail
        ViewCompat.setTransitionName(sharedElement, "imagePokemon")

        val sharedElementTransition = MaterialContainerTransform().apply {
            addTarget(sharedElement)
            duration = resources.getInteger(android.R.integer.config_longAnimTime).toLong()
            startView = activity?.findViewById(R.id.imagePokemon)
            endView = sharedElement
            scrimColor = Color.TRANSPARENT
        }
        sharedElementEnterTransition = sharedElementTransition

        viewModel = ViewModelProvider(
            this,
            PokemonDetailViewModelFactory()
        )[PokemonDetailViewModel::class.java]
        val bundle = arguments
        if(bundle == null) {
            Log.e("Confirmation", "ConfirmationFragment did not receive traveler information")
            return
        }

        val args = PokemonDetailFragmentArgs.fromBundle(bundle)
        val pokedexId = args.pokemonPokedexId
        viewModel.getPokemon(pokedexId)
        viewModel.pokemon.observe(viewLifecycleOwner) {pokemon -> displayPokemon(pokemon)}

        //add a listener to the favorite button
        binding.favoriteButton.setOnClickListener {
            //read from the shared preferences if the pokemon is favorite

            val isFavorite = viewModel.isFavorite(context)
            if(isFavorite) {
                //add to favorite
                binding.favoriteButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_heart_foreground_grey
                    )
                )
                viewModel.removeFromFavorite(context)

            } else {
                //remove from favorite
                binding.favoriteButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_heart_foreground_red
                    )
                )
                viewModel.addToFavorite(context)
            }
        }


    }

    private fun displayPokemon(pokemon : Pokemon?) {
        binding.pokemonDetail = pokemon

        // display pokemon image
        pokemonImage(pokemon)
        // display pokemon type
        pokemonType(pokemon)
        //if the pokemon has a pre evolution, display it
        preEvolution(pokemon)
        //if the pokemon has an evolution, display it
        evolution(pokemon)
        binding.layoutPokemonDetail.setOnClickListener {
            //hide evolutionLayout
            binding.evolutionChooserLayout.root.visibility = View.GONE
            binding.EvolutionImage.visibility = View.VISIBLE
        }
        setliked(pokemon)


    }

    private fun setliked(pokemon : Pokemon?) {
        val isFavorite = pokemon?.isFavorite ?: false
        if(isFavorite) {
            //add to favorite
            binding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_heart_foreground_red
                )
            )
        } else {
            //remove from favorite
            binding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_heart_foreground_grey
                )
            )
        }
    }

    private fun pokemonImage(pokemon : Pokemon?) {
        Glide.with(binding.root.context)
            .load(pokemon?.image)
            .into(binding.imagePokemonDetail)
    }

    private fun evolution(pokemon : Pokemon?) {
        binding.evolutionChooserLayout.root.visibility = View.GONE
        val pkmnevolution = pokemon?.evolution?.toList()

        if(!pkmnevolution.isNullOrEmpty()) {
            if(pkmnevolution.size == 1) {
                binding.evolutionlayout.visibility = View.VISIBLE
                Glide.with(binding.root.context)
                    .load(pokemon.evolution?.get(0)?.image)
                    .into(binding.EvolutionImage)

                binding.EvolutionImage.setOnClickListener {
                    val action =
                        PokemonDetailFragmentDirections.actionPokemonDetailFragmentSelf(
                            pokemon.evolution?.get(0)?.pokedexId!!
                        )
                    findNavController().navigate(action)
                }

            } else {
                val layoutManager = FlexboxLayoutManager(context)
                layoutManager.flexDirection = FlexDirection.ROW
                layoutManager.flexWrap = FlexWrap.WRAP
                layoutManager.alignItems = AlignItems.STRETCH
                layoutManager.justifyContent = JustifyContent.SPACE_EVENLY

                binding.evolutionChooserLayout.layoutManager = layoutManager

                pokemonEvolutionListAdapter =
                    PokemonEvolutionListAdapter(pkmnevolution, this)
                binding.evolutionChooserLayout.adapter = pokemonEvolutionListAdapter


                Glide.with(binding.root.context)
                    .load(pokemon.evolution?.random()?.image)
                    .into(binding.EvolutionImage)

                binding.EvolutionImage.setOnClickListener {
                    //show evolutionLayout
                    println("must show evolutionLayout")
                    binding.evolutionChooserLayout.root.visibility = View.VISIBLE
                    binding.EvolutionImage.visibility = View.GONE
                }


            }
        }

    }

    private fun preEvolution(pokemon : Pokemon?) {
        if(pokemon?.preEvolution != null) {
            binding.preEvolutionImage.visibility = View.VISIBLE
            Glide.with(binding.root.context)
                .load(pokemon.preEvolution?.image)
                .into(binding.preEvolutionImage)

            binding.preEvolutionImage.setOnClickListener {
                val action =
                    PokemonDetailFragmentDirections.actionPokemonDetailFragmentSelf(
                        pokemon.preEvolution?.pokedexId!!
                    )
                findNavController().navigate(action)
            }
        } else binding.preEvolutionImage.visibility = View.GONE
    }

    private fun pokemonType(pokemon : Pokemon?) {
        // set background color
        setBackground(pokemon!!)
        // set pokemon type
        Glide.with(binding.root.context)
            .load(pokemon?.apiTypes?.get(0)?.image)
            .into(binding.imagePokemonType1)

        if(pokemon?.apiTypes?.size == 2) {
            binding.blockType2.visibility = View.VISIBLE
            Glide.with(binding.root.context)
                .load(pokemon.apiTypes[1].image)
                .into(binding.imagePokemonType2)
        } else binding.blockType2.visibility = View.GONE


    }

    private fun setBackground(pokemon : Pokemon) {
        val type = if(pokemon.apiTypes?.size == 1) {
            pokemon.apiTypes[0].name.normalize()
        } else {
            pokemon.apiTypes?.get(1)?.name?.normalize()
        }
        val colorResId = getColorForType(type!!)

        val color = ContextCompat.getColor(requireContext(), colorResId)
        binding.layoutPokemonDetail.setBackgroundColor(color)
    }

    private fun getColorForType(type : String) : Int {
        return when(type.normalize()) {
            "normal"->R.color.colorNormal
            "feu"->R.color.colorFire
            "eau"->R.color.colorWater
            "electrik"->R.color.colorElectric
            "plante"->R.color.colorGrass
            "glace"->R.color.colorIce
            "combat"->R.color.colorFighting
            "poison"->R.color.colorPoison
            "sol"->R.color.colorGround
            "vol"->R.color.colorFlying
            "psy"->R.color.colorPsychic
            "insecte"->R.color.colorBug
            "roche"->R.color.colorRock
            "spectre"->R.color.colorGhost
            "dragon"->R.color.colorDragon
            "tenebres"->R.color.colorDark
            "acier"->R.color.colorSteel
            "fee"->R.color.colorFairy
            else->R.color.white //should never happen
        }
    }

    override fun onPokemonClick(pokemon : Pokemon) {
        val action =
            PokemonDetailFragmentDirections.actionPokemonDetailFragmentSelf(pokemon.pokedexId!!)
        findNavController().navigate(action)
    }
}
