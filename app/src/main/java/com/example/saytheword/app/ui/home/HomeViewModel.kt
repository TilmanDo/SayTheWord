package com.example.saytheword.app.ui.home

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    val homeNavOptionSelected = MutableLiveData<HomeNavOptions>()


    fun onPlayButtonClicked(){

        homeNavOptionSelected.value = HomeNavOptions.PLAY

    }

    fun onShopButtonClicked(){

        homeNavOptionSelected.value = HomeNavOptions.SHOP

    }

    fun onManualButtonClicked(){

        homeNavOptionSelected.value = HomeNavOptions.MANUAL

    }

}