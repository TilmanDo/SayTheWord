package com.example.saytheword.app.ui.pack_select.custom_packs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saytheword.R
import com.example.saytheword.app.ui.pack_select.custom_packs.adapters.CustomPackRecyclerViewAdapter
import com.example.saytheword.data.sample_data.SamplePackData
import com.example.saytheword.databinding.FragmentCustomPackBinding
import com.example.saytheword.domain.models.Card
import com.example.saytheword.domain.models.Pack

class CustomPackFragment: Fragment() {

    lateinit var binding: FragmentCustomPackBinding

    lateinit var basePackRv: RecyclerView

    lateinit var basePackAdapter: CustomPackRecyclerViewAdapter

    lateinit var dirtyPackRv: RecyclerView

    lateinit var dirtyPackAdapter: CustomPackRecyclerViewAdapter

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

        setUpRecyclerViews()

        observeAdapters()

        observeViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpRecyclerViews(){

        //BASE

        basePackRv = binding.fragmentCustomPackBasePackRv

        basePackAdapter = CustomPackRecyclerViewAdapter(SamplePackData.packs[0])

        val basePackLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        basePackRv.apply {

            adapter = basePackAdapter

            layoutManager = basePackLayoutManager

        }

        //DIRTY

        dirtyPackRv = binding.fragmentCustomPackDirtyPackRv

        dirtyPackAdapter = CustomPackRecyclerViewAdapter(SamplePackData.packs[1])

        val dirtyPackLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        dirtyPackRv.apply {

            adapter = dirtyPackAdapter

            layoutManager = dirtyPackLayoutManager

        }


    }

    private fun observeAdapters(){

        //BASE
        val onBasePackCardClickedObserver = Observer<Card>{

            onCardClicked(SamplePackData.packs[0], it)

        }

        basePackAdapter.onCardClicked.observe(viewLifecycleOwner, onBasePackCardClickedObserver)

        //DIRTY
        val onDirtyPackCardClickedObserver = Observer<Card>{

            onCardClicked(SamplePackData.packs[1], it)


        }

        dirtyPackAdapter.onCardClicked.observe(viewLifecycleOwner, onDirtyPackCardClickedObserver)


    }

    private fun observeViewModel(){

        val onCustomPackNavOptionSelectedObserver = Observer<CustomPackNavOptions>{

            navigate(it)

        }

        viewModel.customPackNavOptionSelected.observe(viewLifecycleOwner, onCustomPackNavOptionSelectedObserver)

        val onCardSelectionStatusChangedObserver = Observer<Pair<Pack, Card>> {

            onCardSelectionStatusChanged(it.first, it.second)

        }

        viewModel.cardSelectionStatusChanged.observe(viewLifecycleOwner, onCardSelectionStatusChangedObserver)

        val onNumberOfCardsSelectedChangedObserver = Observer<Int>{

            onNumberOfCardsSelectedChanged(it)

        }

        viewModel.numberOfCardsSelected.observe(viewLifecycleOwner, onNumberOfCardsSelectedChangedObserver)

    }


    private fun onCardClicked(pack: Pack, card: Card){

        viewModel.cardSelectionStatusChanged(pack, card)

    }

    private fun onCardSelectionStatusChanged(pack: Pack, card: Card){

        when(pack.name){


           "Base" -> {

               basePackAdapter.notifyUpdate(card)

           }

           "Dirty" -> {

               dirtyPackAdapter.notifyUpdate(card)

           }


        }

    }

    private fun onNumberOfCardsSelectedChanged(numCards: Int){

        binding.fragmentCustomPackNumCardsTv.text = "$numCards Cards"

    }


    private fun navigate(navOption: CustomPackNavOptions){

        when(navOption){
            CustomPackNavOptions.BACK -> TODO()
        }

    }


}