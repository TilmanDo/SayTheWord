package com.example.saytheword.app.ui.new_game_settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewGameSettingsViewModel: ViewModel() {

    val newGameSettingsNavOptionSelected = MutableLiveData<NewGameSettingsNavOptions>()


    fun onBackArrowPressed(){

        newGameSettingsNavOptionSelected.value = NewGameSettingsNavOptions.BACK

    }


    fun onStartGamePressed(){

        newGameSettingsNavOptionSelected.value = NewGameSettingsNavOptions.START_GAME

    }

}