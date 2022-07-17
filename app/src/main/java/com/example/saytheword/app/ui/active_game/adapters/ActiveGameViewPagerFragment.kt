package com.example.saytheword.app.ui.active_game.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.saytheword.R
import com.example.saytheword.databinding.ItemActiveGameCardViewPagerBinding
import com.example.saytheword.domain.models.Card
import com.example.saytheword.domain.models.game.GameState
import com.example.saytheword.domain.models.game.GameTurn

class ActiveGameViewPagerFragment: Fragment() {

    lateinit var card: Card

    lateinit var turn: GameTurn

    lateinit var state: GameState

    lateinit var binding: ItemActiveGameCardViewPagerBinding

    var countdownTimer = 3

    companion object{

        private const val CARD_KEY = "ARGS_CARD"
        private const val TURN_KEY = "ARGS_TURN"
        private const val STATE_KEY = "ARGS_STATE"
        private const val ROUND_LENGTH_KEY = "ROUND_LENGTH"

        fun newInstance(card: Card, turn: GameTurn, state: GameState) = ActiveGameViewPagerFragment().apply {

            arguments = Bundle().apply {
                putParcelable(CARD_KEY, card)
                putInt(TURN_KEY, turn.ordinal)
                putInt(STATE_KEY, state.ordinal)

            }

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.item_active_game_card_view_pager, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments.let {

            card = it!!.getParcelable(CARD_KEY)!!

            val turnOrdinal = it.getInt(TURN_KEY)
            turn = GameTurn.values()[turnOrdinal]

            val stateOrdinal = it.getInt(STATE_KEY)
            state = GameState.values()[stateOrdinal]


        }

        binding.card = card
        binding.turn = turn
        binding.state = state
        binding.countdown = countdownTimer

        super.onViewCreated(view, savedInstanceState)

    }

    fun updateState(state: GameState){

        this.state = state
        binding.state = this.state

    }

    fun updateCountdownTimer(timer: Int){

        this.countdownTimer = timer
        binding.countdown = this.countdownTimer

    }




}