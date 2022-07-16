package com.example.saytheword.app.ui.pack_select.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.saytheword.R
import com.example.saytheword.databinding.ItemPackSelectViewPagerBinding
import com.example.saytheword.domain.models.Pack

class PackSelectViewPagerFragment: Fragment() {

    lateinit var binding: ItemPackSelectViewPagerBinding

    var pack = Pack.getEmptyPack()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.item_pack_select_view_pager, container, false)

        binding.pack = pack

        binding.lifecycleOwner = this

        return binding.root
    }

    fun setPackData(packData: Pack){

        pack = packData

    }

}