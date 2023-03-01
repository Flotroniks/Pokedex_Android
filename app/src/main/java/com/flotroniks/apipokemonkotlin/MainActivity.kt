package com.flotroniks.apipokemonkotlin

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.flotroniks.apipokemonkotlin.data.extension.putPokemon
import com.flotroniks.apipokemonkotlin.data.models.Pokemon
import com.flotroniks.apipokemonkotlin.ui.OnPokemonClickListener
import com.flotroniks.apipokemonkotlin.ui.pokemonDetail.PokemonDetailFragment
import com.flotroniks.apipokemonkotlin.ui.pokemonList.PokemonListFragment
import com.google.gson.Gson


class MainActivity : AppCompatActivity(), OnPokemonClickListener {
    private lateinit var navController: NavController
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Remove title bar
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        // set notification bar to black
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navController = navHostFragment?.findNavController() ?: throw Exception("Navigation host fragment is null")
        // Set up ActionBar
        setupActionBarWithNavController(navController)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.nav_host_fragment, PokemonListFragment(), "pokemonListFragment")
                .addToBackStack("TAG")
                .commit()

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // Allow the ActionBar to navigate up to the parent destination
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onPokemonClick(pokemon: Pokemon) {
        println(" main Activy pokemon clicked")
  /*  supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, PokemonDetailFragment.newInstance(pokemon))
            .addToBackStack(null)
            .commit()*/
        //navController.navigate(R.id.action_pokemonListFragment_to_pokemonDetailFragment)
       //go to detail fragment and pass pokemon
        //make bundle to pass pokemon
        val bundle = bundleOf("pokemon" to gson.toJson(pokemon))
        //remove pokemon list fragment


        navController.navigate(R.id.action_pokemonListFragment_to_pokemonDetailFragment,bundle)


        //navController.navigate(R.id.action_pokemonListFragment_to_pokemonDetailFragment)
    }



}