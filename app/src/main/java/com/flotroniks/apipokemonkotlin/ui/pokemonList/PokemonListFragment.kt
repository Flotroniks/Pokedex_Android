package com.flotroniks.apipokemonkotlin.ui.pokemonList

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.flotroniks.apipokemonkotlin.R
import com.flotroniks.apipokemonkotlin.data.adapter.PokemonListAdapter
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.data.repository.dao.PokemonDaoImpl
import com.flotroniks.apipokemonkotlin.databinding.FragmentPokemonListBinding
import com.flotroniks.apipokemonkotlin.ui.OnPokemonClickListener
import com.flotroniks.apipokemonkotlin.ui.pokemonDetail.PokemonDetailFragment
import com.google.android.flexbox.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialSharedAxis

class PokemonListFragment : Fragment(), OnPokemonClickListener {

    private lateinit var binding : FragmentPokemonListBinding
    private lateinit var viewModel : PokemonListViewModel
    private lateinit var pokemonListAdapter : PokemonListAdapter
    private val pokemonDao = PokemonDaoImpl()

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        // Inflate the layout for this fragment
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView?.visibility = View.VISIBLE

        val detailFragment = PokemonDetailFragment()
        val bundle = Bundle().apply {
            putString("transition_name", "imagePokemon")
        }
        detailFragment.arguments = bundle

        // Set up shared element transitions
        val transition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
        transition.duration = 500
        //resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        enterTransition = transition
        exitTransition = transition

        // Create the view model
        viewModel =
            ViewModelProvider(this, PokemonListViewModelFactory(pokemonDao))[
                    PokemonListViewModel::class.java]


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }


        // use FlexboxLayoutManager
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.alignItems = AlignItems.STRETCH
        layoutManager.justifyContent = JustifyContent.SPACE_EVENLY

        binding.layoutManager = layoutManager
        // Get the list of pokemon from the view model and pass it to the adapter
        // If the list is null, pass an empty list instead

        val pokemonList = viewModel.pokemonList.value ?: emptyList()
        // Create the adapter
        pokemonListAdapter = PokemonListAdapter(pokemonList, this)
        binding.adapter = pokemonListAdapter
        // Observe the pokemon list from the view model

        viewModel.pokemonSearched.observe(
            viewLifecycleOwner
        ) {pokemonListAdapter.updatePokemonList(it)}

        // Ajouter un TextWatcher pour surveiller la saisie de texte dans la barre de recherche
        binding.searchBar.search.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s : CharSequence?,
                    start : Int,
                    count : Int,
                    after : Int,
                ) {
                    // Ne rien faire
                }

                override fun onTextChanged(
                    s : CharSequence?,
                    start : Int,
                    before : Int,
                    count : Int,
                ) {
                    // Mettre à jour la liste des pokémons en fonction de la saisie de texte
                    viewModel.searchPokemon(s.toString())
                    // if the search bar is empty, show the full list
                    if(s.toString().isEmpty()) {
                        viewModel.pokemonList
                            .observe(viewLifecycleOwner) {
                                pokemonListAdapter.updatePokemonList(it)
                            }
                    }
                }

                override fun afterTextChanged(s : Editable?) {
                    // Ne rien faire
                }
            })


    }

    override fun onPokemonClick(pokemon : Pokemon) {
        /*  // hide the layout
          binding.pokemonList.visibility = View.GONE*/
        val direction =
            PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailFragment(
                pokemon.pokedexId!!
            )
        findNavController().navigate(direction)
    }
}
