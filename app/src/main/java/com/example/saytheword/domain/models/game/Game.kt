package com.example.saytheword.domain.models.game

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.saytheword.R
import com.example.saytheword.domain.models.Pack
import com.google.android.material.card.MaterialCardView

data class Game(var pack: Pack, val gameRound: GameRound, var state: GameState, var pointsToWin: Int, var score: GameScore, var isActive: Boolean){


    fun updateToNextRound(score: GameScore): Game {

        val newGame = this

        newGame.score = score

        newGame.gameRound.startRound()

        if(this.gameRound.turn == GameTurn.RED) {
            newGame.gameRound.turn = GameTurn.BLUE
        } else {
            newGame.gameRound.turn = GameTurn.RED
        }

        return newGame

    }


    companion object{

        fun createNewGame(pack: Pack, roundLength: Int, pointsToWin: Int): Game{

            return Game(pack, GameRound(0, GameTurn.RED, roundLength, roundLength), GameState.COUNTDOWN, pointsToWin, GameScore(0, 0), false)

        }

        @JvmStatic
        @BindingAdapter("app:countdownVisibility")
        fun setCountdownVisibility(view: View, state: GameState){

            if(state == GameState.COUNTDOWN){

                view.visibility = View.VISIBLE

            } else {

                view.visibility = View.GONE

            }

        }

        @JvmStatic
        @BindingAdapter("app:wordVisibility")
        fun setWordVisibility(view: View, state: GameState){

            if(state == GameState.WORD){

                view.visibility = View.VISIBLE

            } else {

                view.visibility = View.GONE

            }

        }

        @JvmStatic
        @BindingAdapter("app:resultVisibility")
        fun setResultVisibility(view: View, state: GameState){

            if(state == GameState.RESULT){

                view.visibility = View.VISIBLE

            } else {

                view.visibility = View.GONE

            }

        }

        @JvmStatic
        @BindingAdapter("app:wordTimerStrokeColor")
        fun setWordTimerStrokeColor(cardView: MaterialCardView, turn: GameTurn){

            when(turn){
                GameTurn.RED -> cardView.strokeColor = ContextCompat.getColor(cardView.context, R.color.colorMain)
                GameTurn.BLUE -> cardView.strokeColor = ContextCompat.getColor(cardView.context, R.color.colorAccent)
            }

        }

        @JvmStatic
        @BindingAdapter("app:wordTimerTextColor")
        fun setWordTimerTextColor(textView: TextView, turn: GameTurn){

            when(turn){
                GameTurn.RED -> textView.setTextColor(ContextCompat.getColor(textView.context, R.color.colorMain))
                GameTurn.BLUE -> textView.setTextColor(ContextCompat.getColor(textView.context, R.color.colorAccent))
            }

        }

        @JvmStatic
        @BindingAdapter("app:wordTimerTextState", "app:wordTimerTextTimer")
        fun setWordTimerText(textView: TextView, state: GameState, timer: Int){

            when(state){
                GameState.COUNTDOWN -> textView.text = "Get Ready!"
                GameState.WORD -> textView.text = timer.toString()
                GameState.RESULT -> textView.text = "Time's up!"
            }

        }




    }




}
