package com.flotroniks.apipokemonkotlin.ui.pokemonList

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.flotroniks.apipokemonkotlin.data.repository.dao.PokemonDao
import com.flotroniks.apipokemonkotlin.data.adapter.PokemonListAdapter
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.data.repository.dao.PokemonDaoImpl
import com.flotroniks.apipokemonkotlin.databinding.FragmentPokemonListBinding
import com.flotroniks.apipokemonkotlin.ui.OnPokemonClickListener
import com.google.android.flexbox.*

class PokemonListFragment : Fragment(), OnPokemonClickListener {

    private lateinit var binding: FragmentPokemonListBinding
    private lateinit var viewModel: PokemonListViewModel
    private lateinit var pokemonListAdapter: PokemonListAdapter
    private val pokemonDao: PokemonDao = PokemonDaoImpl()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Create the view model
        viewModel = ViewModelProvider(this, PokemonListViewModelFactory(pokemonDao))[PokemonListViewModel::class.java]
       /* val layoutManager = LinearLayoutManager(context)
        binding.layoutManager = layoutManager*/
        //use FlexboxLayoutManager

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



        viewModel.pokemonList.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            binding.pokemonList.visibility = View.GONE
            binding.searchBar.root.visibility = View.GONE

            // Mettre à jour la liste des pokémons
            pokemonListAdapter.updatePokemonList(it)
            //during 2 seconds make the loading screen split in half
            // binding.loadingScreenLayout.root.visibility = View.VISIBLE
            val delay:Long = 1750

            binding.loadingScreenLayout.loadingScreenBottom.root.animate().translationYBy(1100f).duration = delay
            binding.loadingScreenLayout.loadingScreenTop.root.animate().translationYBy(-1100f).duration = delay

            // Ajouter un délai de 2 secondes avant de masquer l'écran de chargement
            Handler(Looper.getMainLooper()).postDelayed({
                // Masquer l'écran de chargement après 2 secondes
                binding.loadingScreenLayout.root.visibility = View.GONE
                binding.pokemonList.visibility = View.VISIBLE
                binding.searchBar.root.visibility = View.VISIBLE
            }, delay)


        })
        viewModel.pokemonSearched.observe(viewLifecycleOwner, Observer {

                pokemonListAdapter.updatePokemonList(it)

        } )

// Ajouter un TextWatcher pour surveiller la saisie de texte dans la barre de recherche
        binding.searchBar.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Ne rien faire
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Mettre à jour la liste des pokémons en fonction de la saisie de texte
                viewModel.searchPokemon(s.toString())
                //if the search bar is empty, show the full list
                if (s.toString().isEmpty()){
                    viewModel.pokemonList.observe(viewLifecycleOwner, Observer {
                        pokemonListAdapter.updatePokemonList(it)
                    })
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Ne rien faire
            }
        })
    }
    override fun onPokemonClick(pokemon: Pokemon) {
        //hide the layout
        binding.pokemonList.visibility = View.GONE
        //go to pokemonDetailFragment
       /*(activity as OnPokemonClickListener).onPokemonClick(pokemon)*/
        //go to pokemonDetailFragment
        val direction = PokemonListFragmentDirections
            .actionPokemonListFragmentToPokemonDetailFragment(pokemon.pokedexId!!)
        findNavController().navigate(direction)
    }

}





