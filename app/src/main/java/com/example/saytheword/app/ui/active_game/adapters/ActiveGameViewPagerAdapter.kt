package com.example.saytheword.app.ui.active_game.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.saytheword.domain.models.Card

class ActiveGameViewPagerAdapter(val fragment: Fragment,val cards: ArrayList<Card>): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun createFragment(position: Int): Fragment {


        return ActiveGameViewPagerFragment()
    }


}