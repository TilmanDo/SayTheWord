package com.example.saytheword.app.ui.pack_select.custom_packs

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saytheword.data.sample_data.SamplePackData
import com.example.saytheword.domain.models.Card
import com.example.saytheword.domain.models.Pack

class CustomPackViewModel: ViewModel() {

    val customPackNavOptionSelected = MutableLiveData<CustomPackNavOptions>()

    val numberOfCardsSelected = MutableLiveData<Int>(0)

    private val cards = createNewCardsList()

    val cardSelectionStatusChanged = MutableLiveData<Pair<Pack, Card>>()





    fun cardSelectionStatusChanged(pack: Pack, card: Card){

        val position = findCardPosition(card)

        cards[position].customPackIsSelected = !cards[position].customPackIsSelected

        numberOfCardsSelected.value = getNumberOfSelectedCards()

        cardSelectionStatusChanged.value = Pair(pack, cards[position])


    }

    fun onBackArrowPressed(){

        customPackNavOptionSelected.value = CustomPackNavOptions.BACK

    }

    fun onSaveButtonPressed(){



    }

    private fun createNewCardsList(): ArrayList<Card>{

        val list = ArrayList<Card>()

        for(pack in SamplePackData.packs){

            for(card in pack.cards) {
                list.add(card)
            }

        }

        return list
    }

    private fun findCardPosition(card: Card): Int{

        cards.forEachIndexed{ index, listCard ->

            if(listCard.word == card.word) return index

        }

        return 0
    }

    private fun getNumberOfSelectedCards(): Int{

        var result = 0

        for(card in cards){

            if(card.customPackIsSelected) result++

        }

        return result

    }




}