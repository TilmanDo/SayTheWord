package com.example.saytheword.app.ui.pack_select.custom_packs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.saytheword.R
import com.example.saytheword.app.util.Extensions.Companion.toPx
import com.example.saytheword.databinding.ItemCustomPackCardBinding
import com.example.saytheword.domain.models.Card
import com.example.saytheword.domain.models.Pack
import com.google.android.material.card.MaterialCardView

class CustomPackRecyclerViewAdapter(val pack: Pack) : RecyclerView.Adapter<CustomPackRecyclerViewAdapter.CustomPackViewHolder>() {

    val onCardClicked = MutableLiveData<Card>()


    inner class CustomPackViewHolder(val binding: ItemCustomPackCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomPackViewHolder {
        return CustomPackViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_custom_pack_card, parent, false))
    }

    override fun onBindViewHolder(holder: CustomPackViewHolder, position: Int) {

        holder.binding.pack = pack

        holder.binding.card = pack.cards[position]

        holder.binding.itemCustomPackCardCv.setOnClickListener(View.OnClickListener {

            onCardClicked.value = pack.cards[position]

        })

    }

    override fun getItemCount(): Int {
        return pack.cards.size
    }

    fun notifyUpdate(card: Card){

        val position = findCardPositionInList(card)

        pack.cards[position] = card

        notifyDataSetChanged()

    }


    private fun findCardPositionInList(card: Card): Int{

        pack.cards.forEachIndexed { index, listCard ->

            if(listCard.word == card.word) return index

        }

        return 0
    }




    //BINDING ADAPTERS

    companion object{

        @JvmStatic
        @BindingAdapter("app:customPackCardStyle1", "app:customPackCardStyle2", requireAll = true)
        fun setCardStyle(cardView: MaterialCardView, card: Card, pack: Pack){

            if(card.customPackIsSelected){

                cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, pack.color))


            } else {

                cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.background))

            }

        }

        @JvmStatic
        @BindingAdapter("app:customPackTextColor1", "app:customPackTextColor2", requireAll = true)
        fun setTextColor(textView: TextView, card: Card, pack: Pack){

            if(card.customPackIsSelected){

                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.white))

            } else {

                textView.setTextColor(ContextCompat.getColor(textView.context, pack.color))

            }

        }

    }


}