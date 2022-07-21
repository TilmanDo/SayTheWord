package com.example.saytheword.app.ui.pack_select.custom_packs

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saytheword.R
import com.example.saytheword.app.ui.MainActivity
import com.example.saytheword.app.ui.pack_select.custom_packs.adapters.CustomPackRecyclerViewAdapter
import com.example.saytheword.data.sample_data.SamplePackData
import com.example.saytheword.databinding.FragmentCustomPackBinding
import com.example.saytheword.domain.models.Card
import com.example.saytheword.domain.models.Pack
import com.google.android.material.snackbar.Snackbar

class CustomPackFragment: Fragment() {

    lateinit var activity: MainActivity

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

        activity = requireActivity() as MainActivity

        setUpEditText()

        setUpRecyclerViews()

        observeAdapters()

        observeViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpEditText(){

        binding.fragmentCustomPackPackNameEt.isEnabled = false
        binding.fragmentCustomPackPackNameEt.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.transparent)

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

        val onEditingStatusChangedObserver = Observer<Boolean>{

            onEditingStatusChanged(it)

        }

        viewModel.editButtonPressed.observe(viewLifecycleOwner, onEditingStatusChangedObserver)

        val onCardSelectionStatusChangedObserver = Observer<Pair<Pack, Card>> {

            onCardSelectionStatusChanged(it.first, it.second)

        }

        viewModel.cardSelectionStatusChanged.observe(viewLifecycleOwner, onCardSelectionStatusChangedObserver)

        val onNumberOfCardsSelectedChangedObserver = Observer<Int>{

            onNumberOfCardsSelectedChanged(it)

        }

        viewModel.numberOfCardsSelected.observe(viewLifecycleOwner, onNumberOfCardsSelectedChangedObserver)

    }


    /**
     * Pass the onClick event of a card to the viewModel and tell it to update the card's selected status.
     *
     * @param pack
     * @param card
     */
    private fun onCardClicked(pack: Pack, card: Card){

        viewModel.cardSelectionStatusChanged(pack, card)

    }

    /**
     * Called when the viewModel has executed all logic - updates the fitting adapter with the new card object.
     *
     * @param pack
     * @param card
     */
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

    /**
     * Updates the NumCards TextView with the new number of selected cards.
     *
     * @param numCards
     */
    private fun onNumberOfCardsSelectedChanged(numCards: Int){

        binding.fragmentCustomPackNumCardsTv.text = "$numCards Cards"

    }

    /**
     * When the edit button has been clicked this function updates the look and status of both the EditText and ImageView.
     *
     * @param editing
     */
    private fun onEditingStatusChanged(editing: Boolean){

        val editText = binding.fragmentCustomPackPackNameEt
        val button = binding.fragmentCustomPackPackNameEditIv

        if(editing){

            editText.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorMain)
            button.setImageResource(R.drawable.ic_check_white)
            editText.isEnabled = true

            editText.requestFocus()

            val imm: InputMethodManager =
                requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, 0)

        } else {

            binding.fragmentCustomPackPackNameEt.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.transparent)
            binding.fragmentCustomPackPackNameEditIv.setImageResource(R.drawable.ic_edit_white)
            binding.fragmentCustomPackPackNameEt.isEnabled = false

        }

    }

    private fun handleSaveButtonPress(minCardsReached: Boolean){

        if(minCardsReached){

            val cards = viewModel.getSelectedCards()

            val packTitle = binding.fragmentCustomPackPackNameEt.text.toString()

            SamplePackData.packs.add(Pack(packTitle, cards, R.color.customPack,
                unlocked = true,
                isCustom = true
            ))

            navigate(CustomPackNavOptions.BACK)

        } else {

            val snackBar = Snackbar.make(binding.root, "Please select at least 4 cards", Snackbar.LENGTH_SHORT)

            snackBar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            snackBar.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

            snackBar.show()

        }

    }


    private fun navigate(navOption: CustomPackNavOptions){

        when(navOption){
            CustomPackNavOptions.BACK -> activity.viewModel.navigateBackwards()
            CustomPackNavOptions.SAVE -> handleSaveButtonPress(viewModel.getNumberOfSelectedCards() >= viewModel.MIN_CARDS)
        }

    }


}