package com.example.saytheword.app.ui.active_game

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saytheword.data.sample_data.SamplePackData
import com.example.saytheword.domain.models.game.Game
import com.example.saytheword.domain.models.game.GameState
import kotlinx.coroutines.*

class ActiveGameViewModel: ViewModel() {

    lateinit var job: Job

    val activeGameNavOptionSelected = MutableLiveData<ActiveGameNavOptions>()

    val game = MutableLiveData<Game>(Game.createNewGame(SamplePackData.packs[0], 120, 5))

    val gameCountDownState = MutableLiveData<Int>()

    val wordTimerState = MutableLiveData<Int>()

    val gameState = MutableLiveData<GameState>(GameState.COUNTDOWN)


    /**
     * Starts a new game by initiating the first state (Countdown).
     *
     * @param game The instance of the new game - containing the settings which the user has picked prior to starting a new game.
     */
    fun startGame(game: Game) {

        this.game.value = game

        beginCountDownState()

    }

    /**
     * STATE 1: Countdown.
     *
     * Starts a three second timer and updates the [gameCountDownState] which is observed by the fragment.
     * Upon finishing the timer, updates the [gameState] which is observed by the fragment and begins the next state.
     *
     */
    private fun beginCountDownState(){

        object : CountDownTimer(3000, 1000){

            override fun onTick(p0: Long) {
                gameCountDownState.postValue((p0/1000).toInt())
            }

            override fun onFinish() {
                gameState.postValue(GameState.WORD)
                beginWordState()
            }


        }.start()


    }

    /**
     * STATE 2: WORD
     *
     * Starts a timer with a the round length selected by the user in the settings and updates the {wordTimerState] which is
     * observed by the fragment.
     * Upon finishing the timer, updates the [gameState] which is observed by the fragment and begins the next state.
     *
     */
    fun beginWordState(){

        object : CountDownTimer((game.value!!.gameRound.roundLength * 1000).toLong(), 1000){

            override fun onTick(p0: Long) {
                wordTimerState.postValue((p0/1000).toInt())
            }

            override fun onFinish() {
                gameState.postValue(GameState.RESULT)
                beginResultState()
            }


        }.start()


    }

    fun beginResultState(){



    }

    fun onNextRoundPressed(){

        TODO("Handle Result Input")

        gameState.value = GameState.COUNTDOWN
        beginCountDownState()

    }

}