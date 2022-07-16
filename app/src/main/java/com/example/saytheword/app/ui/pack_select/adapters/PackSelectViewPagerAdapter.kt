package com.example.saytheword.app.ui.pack_select.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.saytheword.data.sample_data.SamplePackData

class PackSelectViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    val data = SamplePackData.packs

    override fun getItemCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment {
        val frag = PackSelectViewPagerFragment()
        frag.setPackData(data[position])
        return frag
    }
}