package com.example.saytheword.app.ui.pack_select.custom_packs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.saytheword.R
import com.example.saytheword.databinding.FragmentCustomPackBinding

class CustomPackFragment: Fragment() {

    lateinit var binding: FragmentCustomPackBinding

    val viewModel: CustomPackViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_pack, container, false)

        binding.fragment = this

        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        observeViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeViewModel(){

        val onCustomPackNavOptionSelectedObserver = Observer<CustomPackNavOptions>{

            navigate(it)

        }

        viewModel.customPackNavOptionSelected.observe(viewLifecycleOwner, onCustomPackNavOptionSelectedObserver)

    }

    private fun navigate(navOption: CustomPackNavOptions){

        when(navOption){
            CustomPackNavOptions.BACK -> TODO()
        }

    }


}