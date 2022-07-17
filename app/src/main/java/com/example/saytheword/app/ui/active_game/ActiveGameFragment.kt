package com.example.saytheword.app.ui.active_game

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
import com.example.saytheword.databinding.FragmentActiveGameBinding
import com.example.saytheword.domain.models.game.Game

class ActiveGameFragment: Fragment() {

    lateinit var binding: FragmentActiveGameBinding

    val viewModel: ActiveGameViewModel by viewModels()

    lateinit var activity: MainActivity

    lateinit var game: Game

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_active_game, container, false)

        binding.fragment = this

        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        activity = requireActivity() as MainActivity

        game = activity.viewModel.activeGame

        binding.game = game

        observeViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeViewModel(){

        val onActiveGameNavOptionSelectedObserver = Observer<ActiveGameNavOptions>{

            navigate(it)

        }

        viewModel.activeGameNavOptionSelected.observe(viewLifecycleOwner, onActiveGameNavOptionSelectedObserver)

    }


    private fun navigate(navOption: ActiveGameNavOptions){

        when(navOption){
            ActiveGameNavOptions.BACK -> TODO()
        }

    }



}