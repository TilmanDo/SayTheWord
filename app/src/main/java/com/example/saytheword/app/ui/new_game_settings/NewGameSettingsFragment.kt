package com.example.saytheword.app.ui.new_game_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.saytheword.R
import com.example.saytheword.app.ui.MainActivity
import com.example.saytheword.databinding.FragmentNewGameSettingsBinding

class NewGameSettingsFragment: Fragment() {

    lateinit var binding: FragmentNewGameSettingsBinding

    val viewModel: NewGameSettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_game_settings, container, false)

        binding.fragment = this

        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        observeViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeViewModel(){

        val onNewGameSettingsNavOptionSelectedObserver = Observer<NewGameSettingsNavOptions>{

            navigate(it)

        }

        viewModel.newGameSettingsNavOptionSelected.observe(viewLifecycleOwner, onNewGameSettingsNavOptionSelectedObserver)

    }

    private fun navigate(navOption: NewGameSettingsNavOptions){

        val activity: MainActivity = requireActivity() as MainActivity

        when(navOption){
            NewGameSettingsNavOptions.BACK -> activity.viewModel.navigateBackwards()
            NewGameSettingsNavOptions.ROUND_LENGTH -> TODO()
            NewGameSettingsNavOptions.GAME_LENGTH -> TODO()
            NewGameSettingsNavOptions.START_GAME -> TODO()
        }

    }


}