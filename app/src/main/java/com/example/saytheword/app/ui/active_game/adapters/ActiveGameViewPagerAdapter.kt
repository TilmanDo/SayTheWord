package com.example.saytheword.app.ui.active_game.adapters

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.saytheword.app.ui.active_game.ActiveGameFragment
import com.example.saytheword.domain.models.Card
import com.example.saytheword.domain.models.game.GameState
import com.example.saytheword.domain.models.game.GameTurn

class ActiveGameViewPagerAdapter(val fragment: ActiveGameFragment, val cards: ArrayList<Card>): FragmentStateAdapter(fragment) {

    private val cardFragments = ArrayList<ActiveGameViewPagerFragment>()

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun createFragment(position: Int): Fragment {

        //Get turn from position [0 -> Red, 1 -> Blue]

        val turn = if(position % 2 == 0) 0 else 1

        val cardFragment = ActiveGameViewPagerFragment.newInstance(cards[position], GameTurn.values()[turn], GameState.COUNTDOWN)

        cardFragments.add(position, cardFragment)

        //Observe Fragment for clicks
        val onWordSaidPressedObserver = Observer<Boolean>{
            fragment.onWordSaidPressed()
        }

        val onWordGuessedPressedObserver = Observer<Boolean>{
            fragment.onWordGuessedPressed()
        }

        val onNextRoundPressedObserver = Observer<Boolean>{
            fragment.onNextRoundButtonPressed()
        }

        cardFragment.onWordSaidPressed.observe(fragment.viewLifecycleOwner, onWordSaidPressedObserver)
        cardFragment.onWordGuessedPressed.observe(fragment.viewLifecycleOwner, onWordGuessedPressedObserver)
        cardFragment.onNextRoundButtonPressed.observe(fragment.viewLifecycleOwner, onNextRoundPressedObserver)

        return cardFragment

    }

    private fun getFragmentAtPosition(position: Int): ActiveGameViewPagerFragment {

        return cardFragments[position]

    }

    fun updateGameState(state: GameState, position:Int){

        if(cardFragments.size != 0) getFragmentAtPosition(position).updateState(state)


    }

    fun updateCountDownTimer(timer: Int, position: Int){

        if(cardFragments.size != 0) {
            getFragmentAtPosition(position).updateCountdownTimer(timer)
        }

    }

    fun updateCardResultInputState(button: Int, selected: Boolean, position: Int){

        if(cardFragments.size != 0){

            getFragmentAtPosition(position).updateCardResultInputState(button, selected)

        }


    }


}