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

    val roundLengthData = arrayOf("60s", "90s", "120s", "180s")

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

        setUpNumberPickers()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeViewModel(){

        val onNewGameSettingsNavOptionSelectedObserver = Observer<NewGameSettingsNavOptions>{

            navigate(it)

        }

        viewModel.newGameSettingsNavOptionSelected.observe(viewLifecycleOwner, onNewGameSettingsNavOptionSelectedObserver)

    }

    private fun setUpNumberPickers(){


        //Round Length

        val roundLengthNp = binding.fragmentNewGameSettingsRoundLengthNp



        roundLengthNp.minValue = 1
        roundLengthNp.maxValue = roundLengthData.size
        roundLengthNp.displayedValues = roundLengthData
        roundLengthNp.value = 2


        //Points to win

        val pointsToWinNP = binding.fragmentNewGameSettingsPointsToWinNp

        pointsToWinNP.minValue = 3
        pointsToWinNP.maxValue = 15
        pointsToWinNP.value = 5

    }

    private fun navigate(navOption: NewGameSettingsNavOptions){

        val activity: MainActivity = requireActivity() as MainActivity

        when(navOption){
            NewGameSettingsNavOptions.BACK -> activity.viewModel.navigateBackwards()
            NewGameSettingsNavOptions.START_GAME -> {

                //TESTING
                //activity.viewModel.setGameRoundLength(getCurrentRoundLength())
                activity.viewModel.setGameRoundLength(10)

                activity.viewModel.setGamePointsToWin(getCurrentPointsToWin())

                activity.viewModel.navigateForwards(R.id.activeGameFragment)

            }
        }

    }

    private fun getCurrentRoundLength(): Int{

        val np = binding.fragmentNewGameSettingsRoundLengthNp

        val valueString = roundLengthData[np.value]

        return  valueString.dropLast(1).toInt()

    }

    private fun getCurrentPointsToWin(): Int {

        return binding.fragmentNewGameSettingsPointsToWinNp.value

    }


}