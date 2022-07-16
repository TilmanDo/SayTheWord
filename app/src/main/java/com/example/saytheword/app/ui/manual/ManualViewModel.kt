package com.example.saytheword.app.ui.manual

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ManualViewModel: ViewModel() {

    val manualNavOptionsSelected = MutableLiveData<ManualNavOptions>()
}