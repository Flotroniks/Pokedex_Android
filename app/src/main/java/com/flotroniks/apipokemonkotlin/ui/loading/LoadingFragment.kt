package com.flotroniks.apipokemonkotlin.ui.loading

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
import com.flotroniks.apipokemonkotlin.R
import com.flotroniks.apipokemonkotlin.data.livedata.State.*
import com.flotroniks.apipokemonkotlin.data.repository.dao.PokemonDaoImpl
import com.flotroniks.apipokemonkotlin.databinding.FragmentLoadingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoadingFragment : Fragment() {
    private lateinit var viewModel : LoadingViewModel
    private lateinit var binding : FragmentLoadingBinding
    private val pokemonDao = PokemonDaoImpl()
    private val delay : Long = 1750
    private lateinit var navController : NavController
    private val action = LoadingFragmentDirections.actionLoadingFragmentToPokemonListFragment()

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        binding = FragmentLoadingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val dialog =
            AlertDialog.Builder(requireContext())
                .setTitle("Erreur")
                .setMessage("Une erreur s'est produite, veuillez réessayer plus tard")
                .setPositiveButton("Réessayer") {dialog, _ ->
                    dialog.dismiss()
                    // retry the request
                    viewModel.getPokemonList()
                }
                .setNegativeButton("Quittez l'application") {dialog, _ ->
                    dialog.dismiss()
                    // finish the app
                    requireActivity().finish()
                }
                .setCancelable(false)
        navController = findNavController()
        viewModel =
            ViewModelProvider(
                this,
                LoadingViewModelFactory(pokemonDao)
            )[LoadingViewModel::class.java]

        viewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                SUCCESS-> {
                    // remove the dialog
                    dialog.create().dismiss()
                    changeFragment()
                    navController.navigate(action)
                }
                ERROR-> {
                    binding.progressBar.visibility = View.GONE
                    dialog.show()
                }
                else-> {}
            }
        }
    }

    override fun onAttach(context : Context) {
        super.onAttach(context)
        // hide the bottom navigation view
        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView?.visibility = View.GONE
    }

    override fun onDetach() {
        super.onDetach()
        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView?.visibility = View.VISIBLE
    }

    private fun changeFragment() {
        binding.progressBar.visibility = View.GONE
        // during the delay seconds make the loading screen split in half
        binding.loadingScreenLayout.loadingScreenBottom.root.animate()
            .translationYBy(1100f).duration =
            delay
        binding.loadingScreenLayout.loadingScreenTop.root
            .animate()
            .withEndAction {viewModel.animationEnded()}
            .translationYBy(-1100f)
            .duration = delay
    }

    /*  viewModel.animationEnded.observe(viewLifecycleOwner) { animationEnded ->
        if (animationEnded) {
            navController.navigate(action)
        }
    }*/

}
