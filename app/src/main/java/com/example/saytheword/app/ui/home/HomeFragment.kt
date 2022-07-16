package com.example.saytheword.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.saytheword.app.ui.MainActivity
import com.example.saytheword.R
import com.example.saytheword.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {


    lateinit var binding: FragmentHomeBinding

    private val viewModel : HomeViewModel by viewModels()

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.fragment

        binding.viewmodel = viewModel

        return binding.root
    }

    /**
     * Retrieves current NavController and begins observing the [viewModel] for events.
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = (requireActivity() as MainActivity).navController

        observeViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Checks viewmodel for events and handles navigation by calling [navigate] with the received [HomeNavOptions]
     */
    private fun observeViewModel(){

        val onNavOptionSelectedObserver = Observer<HomeNavOptions> {

            navigate(it)

        }

        viewModel.homeNavOptionSelected.observe(viewLifecycleOwner, onNavOptionSelectedObserver)

    }


    private fun navigate(navOptions: HomeNavOptions){

        val activity : MainActivity = requireActivity() as MainActivity

        when(navOptions){
            HomeNavOptions.PLAY -> activity.viewModel.navigateForwards(R.id.packSelectFragment)
            HomeNavOptions.SHOP -> activity.viewModel.navigateForwards(R.id.shopFragment)
            HomeNavOptions.MANUAL -> activity.viewModel.navigateForwards(R.id.manualFragment)
        }

    }

}