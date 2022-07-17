package com.example.saytheword.domain.models.game

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.saytheword.R
import com.google.android.material.card.MaterialCardView

enum class GameTurn {

    RED,
    BLUE;

    companion object {

        @JvmStatic
        @BindingAdapter("app:activeGameCardBackgroundColor")
        fun setCardBackgroundColor(cardView: MaterialCardView, turn: GameTurn){

            when(turn){
                RED -> cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.colorMain))
                BLUE -> cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.colorAccent))
            }

        }

    }


}