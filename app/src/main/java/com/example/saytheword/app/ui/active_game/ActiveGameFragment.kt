package com.example.saytheword.app.ui.active_game

import android.app.AlertDialog
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.saytheword.R
import com.example.saytheword.app.ui.MainActivity
import com.example.saytheword.app.ui.active_game.adapters.ActiveGameViewPagerAdapter
import com.example.saytheword.app.util.Extensions.Companion.toPx
import com.example.saytheword.data.sample_data.SamplePackData
import com.example.saytheword.databinding.FragmentActiveGameBinding
import com.example.saytheword.domain.models.game.Game
import com.example.saytheword.domain.models.game.GameState
import com.example.saytheword.domain.models.game.GameTurn
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.LinearProgressIndicator

class ActiveGameFragment: Fragment() {

    lateinit var binding: FragmentActiveGameBinding

    val viewModel: ActiveGameViewModel by viewModels()

    lateinit var activity: MainActivity

    lateinit var viewPager: ViewPager2

    lateinit var progressBar: LinearProgressIndicator

    lateinit var adapter: ActiveGameViewPagerAdapter

    var game = Game.createNewGame(SamplePackData.packs[0], 120, 5)



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_active_game, container, false)

        binding.fragment = this

        binding.viewmodel = viewModel

        return binding.root
    }

    /**
     *
     * Sets up the ViewPager & begins observing the viewModel for updates in the game.
     *
     * If the viewModels game is not running we take the setUp Values from the activity's viewModel, otherwise we use the active
     * one.
     *
     *
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        activity = requireActivity() as MainActivity

        setUpProgressBar()

        observeViewModel()

        if(!viewModel.game.value!!.isActive) {
            activity.viewModel.gameSetUp.isActive = true
            viewModel.startGame(activity.viewModel.gameSetUp)
        }

        game = viewModel.game.value!!


        binding.game = game


        setUpViewPager()

        updateGameState(game.state)

        super.onViewCreated(view, savedInstanceState)
    }

    /**
     *
     * Delegates updates in the game to the corresponding functions.
     *
     */
    private fun observeViewModel(){

        val onActiveGameNavOptionSelectedObserver = Observer<ActiveGameNavOptions>{

            navigate(it)

        }

        viewModel.activeGameNavOptionSelected.observe(viewLifecycleOwner, onActiveGameNavOptionSelectedObserver)

        val onGameStateUpdateObserver = Observer<GameState>{

            updateGameState(it)

        }

        viewModel.gameState.observe(viewLifecycleOwner, onGameStateUpdateObserver)

        val onGameUpdateObserver = Observer<Game>{

            updateGame(it)

        }

        viewModel.game.observe(viewLifecycleOwner, onGameUpdateObserver)

        val onCountDownTimerStateObserver = Observer<Int>{

            updateCountDownState(it)

        }

        viewModel.gameCountDownState.observe(viewLifecycleOwner, onCountDownTimerStateObserver)

        val onWordTimerStateUpdateObserver = Observer<Int>{

            updateWordTimerState(it)

        }

        viewModel.wordTimerState.observe(viewLifecycleOwner, onWordTimerStateUpdateObserver)

        val onResultInputStateChangedObserver = Observer<Pair<Int, Boolean>>{

            updateResultInputState(it.first, it.second)

        }

        viewModel.resultInputState.observe(viewLifecycleOwner, onResultInputStateChangedObserver)


        val onRoundChangeObserver = Observer<Boolean>{

            swipeToNextRound()

        }

        viewModel.roundChange.observe(viewLifecycleOwner, onRoundChangeObserver)



    }

    /**
     * Updates the fragment's instance of the current game object and the binding variable [Not sure if the latter is necessary]
     *
     * @param game The updated game instance
     */
    private fun updateGame(game: Game){

        this.game = game
        binding.game = game

    }

    /**
     * Tells the ViewPager adapter that the state of the game has changed.
     *
     * Also updates the timer to match the new state.
     *
     * @param state
     */
    private fun updateGameState(state: GameState){

        Log.d("State", state.toString())

        adapter.updateGameState(state, game.gameRound.roundNumber)

        //Update text for countdown cardView
        when(state){
            GameState.COUNTDOWN -> binding.fragmentActiveGameWordTimerTv.text = "Get Ready!"
            GameState.WORD -> binding.fragmentActiveGameWordTimerTv.text = "${game.gameRound.roundLength}s"
            GameState.RESULT -> binding.fragmentActiveGameWordTimerTv.text = "Time's up"
        }

    }

    /**
     * Tells the adapter that the countdown has advanced.
     * Only called during the countdown state.
     *
     * @param timer The updated value of the countdown timer.
     */
    private fun updateCountDownState(timer: Int){

        adapter.updateCountDownTimer(timer, game.gameRound.roundNumber)

    }

    /**
     * Tells the adapter that the word timer has advanced.
     * Only called during the word state.
     *
     * @param timer The updated value of the word timer.
     */
    private fun updateWordTimerState(timer: Int){

        binding.fragmentActiveGameWordTimerTv.text = "${timer}s"

        progressBar.progress = game.gameRound.roundLength - timer + 1

        Log.d("Progress", progressBar.progress.toString())

    }

    /**
     * Creates and sets the ViewPager's adapter and does all the necessary visual adjustments.
     */
    private fun setUpViewPager(){

        viewPager = binding.fragmentActiveGameCardVp

        val vpAdapter = ActiveGameViewPagerAdapter(this, game.pack.cards)

        adapter = vpAdapter

        viewPager.apply {

            this.adapter = vpAdapter

            this.isUserInputEnabled = false

            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2

            val offsetPx = 30.toPx()

            this.setPadding(offsetPx, 0, offsetPx, 0)

            this.setPageTransformer(MarginPageTransformer(20.toPx()))

        }

    }

    private fun setUpProgressBar(){

        progressBar = binding.fragmentActiveGameWordTimerProgressBar

        val color = if(game.gameRound.turn == GameTurn.RED) R.color.colorMain else R.color.colorAccent

        progressBar.setIndicatorColor(ContextCompat.getColor(progressBar.context, color))

    }

    /**
     * Tells the viewPagerAdapter that the state of the result input buttons have changed.
     *
     * @param button 0 -> WordSaid, 1 -> WordGuessed
     * @param selected
     */
    private fun updateResultInputState(button: Int, selected: Boolean){

        adapter.updateCardResultInputState(button, selected, game.gameRound.roundNumber)

    }

    /**
     * Intercepts clicks on the individual card's word-said button (because it doesn't have a reference to the viewmodel)
     * and forwards it accordingly.
     */
    fun onWordSaidPressed(){

        viewModel.onWordSaidPressed()

    }

    /**
     * Intercepts clicks on the individual card's word-guessed button (because it doesn't have a reference to the viewmodel)
     * and forwards it accordingly.
     */
    fun onWordGuessedPressed(){

        viewModel.onWordGuessedPressed()

    }


    /**
     * Intercepts clicks on the individual card's next-round button (because it doesn't have a reference to the viewmodel)
     * and forwards it accordingly.
     */
    fun onNextRoundButtonPressed(){

        viewModel.onNextRoundPressed()

    }

    fun onPauseButtonPressed(){

        viewModel.onPausePressed()

        if(viewModel.gamePaused){

            binding.fragmentActivePauseFab.icon = ContextCompat.getDrawable(binding.fragmentActivePauseFab.context, R.drawable.ic_play_white)

        } else {

            binding.fragmentActivePauseFab.icon = ContextCompat.getDrawable(binding.fragmentActivePauseFab.context, R.drawable.ic_pause_white)

        }

    }

    fun onQuitButtonPressed(){

        showQuitDialog()

    }

    /**
     * Programmatically moves the viewPager to the next position and sets the correct color for the progress bar.
     *
     */
    private fun swipeToNextRound(){

        viewPager.currentItem = viewPager.currentItem + 1

        val color = if(game.gameRound.turn == GameTurn.RED) R.color.colorMain else R.color.colorAccent

        progressBar.setIndicatorColor(ContextCompat.getColor(progressBar.context, color))

        progressBar.progress = 0


    }

    /**
     * Shows the dialog containing a negative button (simply cancels the dialog) and a positive button (calls [quitGame])
     *
     */
    private fun showQuitDialog(){

        val dialogBuilder = MaterialAlertDialogBuilder(requireContext())

        dialogBuilder.apply {

            setCancelable(false)
            setTitle("Quit Game")
            setMessage("Are you sure you want to quit? All progress will be lost.")
            setPositiveButton("Quit") {dialog, which ->

                quitGame()

            }

            setNegativeButton("Cancel") {dialog, which ->

                dialog.dismiss()

            }

        }

        val dialog = dialogBuilder.create()

        dialog.show()

    }

    private fun quitGame(){

        viewModel.quitGame()
        navigate(ActiveGameNavOptions.BACK)

    }


    private fun navigate(navOption: ActiveGameNavOptions){

        when(navOption){
            ActiveGameNavOptions.BACK -> activity.viewModel.navigateHome(true)
        }

    }

    //Set game status to !notActive when user navigates away from the game
    //TODO(Intercept back action when this fragment is on top of the backstack and show confirmation dialog)
    override fun onDestroyView() {

        activity.viewModel.gameSetUp.isActive = false

        super.onDestroyView()

    }








}