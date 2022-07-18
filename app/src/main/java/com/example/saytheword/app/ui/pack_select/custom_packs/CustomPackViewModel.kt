package com.example.saytheword.app.ui.pack_select.custom_packs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomPackViewModel: ViewModel() {

    val customPackNavOptionSelected = MutableLiveData<CustomPackNavOptions>()



    fun onBackArrowPressed(){

        customPackNavOptionSelected.value = CustomPackNavOptions.BACK

    }


}