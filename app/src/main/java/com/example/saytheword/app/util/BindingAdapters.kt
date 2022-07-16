package com.example.saytheword.app.util

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.databinding.BindingAdapter
import com.example.saytheword.domain.models.Pack
import com.google.android.material.card.MaterialCardView

class BindingAdapters {

    companion object{

        @BindingAdapter("card_view_background_color")
        fun cardColor(cardView: MaterialCardView, pack: Pack){

            cardView.setBackgroundColor(pack.color)

        }

    }


}