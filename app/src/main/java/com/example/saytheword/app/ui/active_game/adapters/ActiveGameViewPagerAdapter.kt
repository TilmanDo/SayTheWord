package com.example.saytheword.app.ui.active_game.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.saytheword.domain.models.Card
import com.example.saytheword.domain.models.game.GameState
import com.example.saytheword.domain.models.game.GameTurn
import kotlin.math.round

class ActiveGameViewPagerAdapter(val fragment: Fragment,val cards: ArrayList<Card>): FragmentStateAdapter(fragment) {

    val cardFragments = ArrayList<ActiveGameViewPagerFragment>()

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun createFragment(position: Int): Fragment {

        //Get turn from position [0 -> Red, 1 -> Blue]

        val turn = if(position % 2 == 0) 0 else 1

        val fragment = ActiveGameViewPagerFragment.newInstance(cards[position], GameTurn.values()[turn], GameState.COUNTDOWN)

        cardFragments.add(position, fragment)

        return fragment

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


}