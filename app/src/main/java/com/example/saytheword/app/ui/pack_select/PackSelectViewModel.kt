package com.example.saytheword.app.ui.pack_select

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saytheword.data.sample_data.SamplePackData
import com.example.saytheword.domain.models.Pack

class PackSelectViewModel: ViewModel() {

    val packSelectNavOptionSelected = MutableLiveData<PackSelectNavOptions>()

    val nextButtonStateChanged = MutableLiveData<Boolean>()

    var currentlySelectedPack : Pack = SamplePackData.packs[0]

    fun onBackArrowPressed(){

        packSelectNavOptionSelected.value = PackSelectNavOptions.BACK

    }

    fun onNextButtonPressed(){

        if(currentlySelectedPack.unlocked){

            packSelectNavOptionSelected.value = PackSelectNavOptions.NEXT

        }

    }

    fun setSelectedPack(pack: Pack){

        currentlySelectedPack = pack

        nextButtonStateChanged.value = currentlySelectedPack.unlocked

    }

}