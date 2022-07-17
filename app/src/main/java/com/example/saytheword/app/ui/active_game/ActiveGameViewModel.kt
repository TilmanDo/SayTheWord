package com.example.saytheword.app.ui.active_game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActiveGameViewModel: ViewModel() {

    val activeGameNavOptionSelected = MutableLiveData<ActiveGameNavOptions>()

}