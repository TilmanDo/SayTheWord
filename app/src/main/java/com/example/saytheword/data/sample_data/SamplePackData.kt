package com.example.saytheword.data.sample_data

import com.example.saytheword.R
import com.example.saytheword.domain.models.Card
import com.example.saytheword.domain.models.Pack

object SamplePackData {

    val packs = arrayListOf<Pack>(
        Pack(
            "Base",
            arrayListOf(Card("Prisoner"), Card("Moon"), Card("Champagne"), Card("Shelter")),
            R.color.colorMain,
            true
        ), Pack("Dirty", arrayListOf(), R.color.colorAccent, false),
        Pack("Sport", arrayListOf(), R.color.colorAccent, false)
    )
}
