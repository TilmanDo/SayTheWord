package com.example.saytheword.app.ui.pack_select.custom_packs

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saytheword.data.sample_data.SamplePackData
import com.example.saytheword.domain.models.Card
import com.example.saytheword.domain.models.Pack

class CustomPackViewModel: ViewModel() {

    val MIN_CARDS = 4

    val customPackNavOptionSelected = MutableLiveData<CustomPackNavOptions>()

    var editing = false

    val editButtonPressed = MutableLiveData<Boolean>()

    val numberOfCardsSelected = MutableLiveData<Int>(0)

    private val cards = createNewCardsList()

    val cardSelectionStatusChanged = MutableLiveData<Pair<Pack, Card>>()


    /**
     * Inverts the selected field of the card, updates the number of selected cards and the MutableLiveData which the fragment observers.
     *
     * @param pack The pack to which the card belongs.
     * @param card The card which has been clicked and whose status needs to be updated.
     */
    fun cardSelectionStatusChanged(pack: Pack, card: Card){

        val position = findCardPosition(card)

        cards[position].customPackIsSelected = !cards[position].customPackIsSelected

        numberOfCardsSelected.value = getNumberOfSelectedCards()

        cardSelectionStatusChanged.value = Pair(pack, cards[position])


    }

    fun onBackArrowPressed(){

        customPackNavOptionSelected.value = CustomPackNavOptions.BACK

    }

    fun onEditButtonPressed(){

        editing = !editing

        editButtonPressed.value = editing

    }

    fun onSaveButtonPressed(){

        customPackNavOptionSelected.value = CustomPackNavOptions.SAVE

    }

    /**
     * Finds the position of a card in the viewmodel's list of cards.
     *
     * @param card
     * @return
     */
    private fun findCardPosition(card: Card): Int{

        cards.forEachIndexed{ index, listCard ->

            if(listCard.word == card.word) return index

        }

        return 0
    }


    fun getNumberOfSelectedCards(): Int{

        var result = 0

        for(card in cards){

            if(card.customPackIsSelected) result++

        }

        return result

    }

    fun getSelectedCards(): ArrayList<Card>{

        val list = ArrayList<Card>()

        for(card in cards) if(card.customPackIsSelected) list.add(card)

        return list

    }

    /**
     * Creates a list containing all cards from all packs.
     *
     * @return
     */
    private fun createNewCardsList(): ArrayList<Card>{

        val list = ArrayList<Card>()

        for(pack in SamplePackData.packs){

            for(card in pack.cards) {
                list.add(card)
            }

        }

        return list
    }




}