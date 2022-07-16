package com.example.saytheword.app.ui.manual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.saytheword.R
import com.example.saytheword.databinding.FragmentManualBinding

class ManualFragment: Fragment() {

    lateinit var binding: FragmentManualBinding

    lateinit var navController: NavController

    private val viewModel: ManualViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manual, container, false)

        binding.fragment = this

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

        navController = Navigation.findNavController(view)

        observeViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Checks ViewModel for events and handles navigation by calling [navigate] with the received [ManualNavOptions]
     */
    private fun observeViewModel(){

        val onNavOptionSelectedObserver = Observer<ManualNavOptions>{

            navigate(it)

        }

        viewModel.manualNavOptionsSelected.observe(viewLifecycleOwner, onNavOptionSelectedObserver)


    }

    private fun navigate(navOption: ManualNavOptions){



    }

}