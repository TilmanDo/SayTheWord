package com.example.saytheword.app.ui.active_game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saytheword.data.sample_data.SamplePackData
import com.example.saytheword.domain.models.game.Game

class ActiveGameViewModel: ViewModel() {


    val activeGameNavOptionSelected = MutableLiveData<ActiveGameNavOptions>()

    var game = Game.createNewGame(SamplePackData.packs[0], 120, 5)



}