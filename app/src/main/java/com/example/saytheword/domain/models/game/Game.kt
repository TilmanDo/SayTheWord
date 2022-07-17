package com.example.saytheword.domain.models.game

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.saytheword.domain.models.Pack

data class Game(var pack: Pack, val gameRound: GameRound, var state: GameState, var pointsToWin: Int, val score: GameScore){


    companion object{

        fun createNewGame(pack: Pack, roundLength: Int, pointsToWin: Int): Game{

            return Game(pack, GameRound(1, roundLength, roundLength), GameState.COUNTDOWN, pointsToWin, GameScore(0, 0))

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




    }




}
