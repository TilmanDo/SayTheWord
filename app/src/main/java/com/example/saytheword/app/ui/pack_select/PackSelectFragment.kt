package com.example.saytheword.app.ui.pack_select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.saytheword.R
import com.example.saytheword.app.ui.MainActivity
import com.example.saytheword.app.ui.pack_select.adapters.PackSelectViewPagerAdapter
import com.example.saytheword.app.util.Extensions.Companion.toPx
import com.example.saytheword.data.sample_data.SamplePackData
import com.example.saytheword.databinding.FragmentPackSelectBinding

class PackSelectFragment: Fragment() {

    lateinit var binding: FragmentPackSelectBinding

    lateinit var navController: NavController

    private val viewModel: PackSelectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pack_select, container, false)

        binding.fragment = this

        binding.viewmodel = viewModel

        return binding.root
    }

    /**
     * Retrieves current NavController and begins observing the [viewModel] for events. Also calls [setUpViewPager].
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = Navigation.findNavController(view)

        observeViewModel()

        setUpViewPager()

        super.onViewCreated(view, savedInstanceState)
    }


    /**
     * Checks ViewModel for events and handles navigation by calling [navigate] with the received [PackSelectNavOptions]
     */
    private fun observeViewModel(){

        val onNavOptionSelectedObserver = Observer<PackSelectNavOptions>{

            navigate(it)

        }

        viewModel.packSelectNavOptionSelected.observe(viewLifecycleOwner, onNavOptionSelectedObserver)

        val onNextButtonStateChangedObserver = Observer<Boolean>{

            updateNextButtonState(it)

        }

        viewModel.nextButtonStateChanged.observe(viewLifecycleOwner, onNextButtonStateChangedObserver)

    }

    /**
     * Sets Adapter, margins and onPageChangeCallback for the viewpager
     */
    private fun setUpViewPager(){


        val viewPager = binding.fragmentPackSelectVp

        val adapter = PackSelectViewPagerAdapter(this)

        viewPager.apply {

            this.adapter = adapter

            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2

            val offsetPx = 30.toPx()

            this.setPadding(offsetPx, 0, offsetPx, 0)

            this.setPageTransformer(MarginPageTransformer(20.toPx()))

        }

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {

                viewModel.setSelectedPack(SamplePackData.packs[position])

                super.onPageSelected(position)
            }

        })


    }

    /**
     * Visually enables/disables the next button based on the unlocked status of a pack.
     *
     * @param unlocked
     */
    private fun updateNextButtonState(unlocked: Boolean){

        if(unlocked){

            binding.fragmentPackSelectNextButtonCv.alpha = 1f
            binding.fragmentPackSelectNextButtonTv.alpha = 1f
            binding.fragmentPackSelectNextButtonCv.isClickable = true

        } else {

            binding.fragmentPackSelectNextButtonCv.alpha = 0.5f
            binding.fragmentPackSelectNextButtonTv.alpha = 0.5f
            binding.fragmentPackSelectNextButtonCv.isClickable = false


        }

    }

    private fun navigate(navOption: PackSelectNavOptions){

        val activity: MainActivity = requireActivity() as MainActivity

        when(navOption){
            PackSelectNavOptions.NEXT -> {

                activity.viewModel.setGamePack(viewModel.currentlySelectedPack)

                activity.viewModel.navigateForwards(R.id.newGameSettingsFragment)

            }
            PackSelectNavOptions.BACK -> activity.viewModel.navigateBackwards()
        }

    }



}