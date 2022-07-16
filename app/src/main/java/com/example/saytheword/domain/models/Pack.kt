package com.example.saytheword.domain.models

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.saytheword.R
import com.google.android.material.card.MaterialCardView

data class Pack(val name: String, val cards: ArrayList<Card>, val color: Int, val unlocked: Boolean) {

    companion object{

        fun getEmptyPack(): Pack {

            return Pack("", arrayListOf(), R.color.background, false)

        }

        @JvmStatic
        @BindingAdapter("app:backgroundColor")
        fun setCardBackGroundColor(cardView: MaterialCardView, color: Int){

            cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, color))

        }

        @JvmStatic
        @BindingAdapter("cardAlpha")
        fun setCardAlpha(cardView: MaterialCardView, unlocked: Boolean){

            if(!unlocked){
                cardView.alpha = 0.5f
            } else {
                cardView.alpha = 1f
            }

        }

        @JvmStatic
        @BindingAdapter("lockVisibility")
        fun setLockIconVisibility(imageView: ImageView, unlocked: Boolean){

            if(!unlocked){
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.GONE
            }

        }

    }

}