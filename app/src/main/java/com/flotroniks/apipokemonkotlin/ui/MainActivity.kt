package com.flotroniks.apipokemonkotlin.ui

import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.flotroniks.apipokemonkotlin.R
import com.flotroniks.apipokemonkotlin.ui.loading.LoadingFragment
import com.flotroniks.apipokemonkotlin.ui.pokemonLiked.PokemonLikedFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState : Bundle?) {

        super.onCreate(savedInstanceState)
        // Remove title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        // set notification bar to black
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        // Set up ActionBar
        // setupActionBarWithNavController(navController)

        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.nav_host_fragment, LoadingFragment(), "LoadingFragment")
                .addToBackStack("TAG")
                .commit()
        }
        //when the user click on the bottom navigation bar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {item ->
            when(item.itemId) {
                R.id.page_1-> {
                    //go to the first fragment
                    navController.navigate(R.id.pokemonListFragment)
                    true
                }
                R.id.page_2-> {
                    //navController.navigate(R.id.favoritePokemonFragment)
                    Toast.makeText(this, "page 2", Toast.LENGTH_SHORT).show()
                    //go to the fragment pokemon liked
                    navController.navigate(R.id.pokemonLikedFragment)

                    true
                }
                else->false
            }
        }

    }

    override fun onSupportNavigateUp() : Boolean {
        // Allow the ActionBar to navigate up to the parent destination
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
