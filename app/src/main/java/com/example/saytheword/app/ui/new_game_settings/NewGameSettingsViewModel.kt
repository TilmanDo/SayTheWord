package com.example.saytheword.app.ui.new_game_settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewGameSettingsViewModel: ViewModel() {

    val newGameSettingsNavOptionSelected = MutableLiveData<NewGameSettingsNavOptions>()


    fun onBackArrowPressed(){

        newGameSettingsNavOptionSelected.value = NewGameSettingsNavOptions.BACK

    }

    fun onRoundLengthPressed(){

        newGameSettingsNavOptionSelected.value = NewGameSettingsNavOptions.ROUND_LENGTH

    }

    fun onGameLengthPressed(){

        newGameSettingsNavOptionSelected.value = NewGameSettingsNavOptions.GAME_LENGTH

    }

    fun onStartGamePressed(){

        newGameSettingsNavOptionSelected.value = NewGameSettingsNavOptions.START_GAME

    }

}