package com.example.saytheword.app.ui.pack_select.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.saytheword.R
import com.example.saytheword.app.ui.MainActivity
import com.example.saytheword.databinding.ItemPackSelectAddCustomPackBinding

class PackSelectViewPagerFragmentAddCustom: Fragment() {

    lateinit var binding: ItemPackSelectAddCustomPackBinding

    lateinit var activity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.item_pack_select_add_custom_pack, container, false)

        binding.fragment = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        activity = requireActivity() as MainActivity

    }


    fun onAddCustomPackPressed(){

        activity.viewModel.navigateForwards(R.id.customPackFragment)

    }



}