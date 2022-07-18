package com.example.saytheword.app.ui.active_game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saytheword.data.sample_data.SamplePackData
import com.example.saytheword.domain.models.game.Game
import com.example.saytheword.domain.models.game.GameScore
import com.example.saytheword.domain.models.game.GameState
import kotlinx.coroutines.*

class ActiveGameViewModel: ViewModel() {

    lateinit var job: Job

    val activeGameNavOptionSelected = MutableLiveData<ActiveGameNavOptions>()

    val game = MutableLiveData<Game>(Game.createNewGame(SamplePackData.packs[0], 10, 5))

    val gameCountDownState = MutableLiveData<Int>()

    val wordTimerState = MutableLiveData<Int>()

    val gameState = MutableLiveData<GameState>(GameState.COUNTDOWN)

    val resultInputState = MutableLiveData<Pair<Int, Boolean>>()

    val roundChange = MutableLiveData<Boolean>()

    var wordSaid = false

    var wordGuessed = false

    var gamePaused = false


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
                gameCountDownState.postValue((p0/1000 + 1).toInt())
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
                wordTimerState.postValue((p0/1000 + 1).toInt())
            }

            override fun onFinish() {
                gameState.postValue(GameState.RESULT)
                beginResultState()
            }


        }.start()


    }

    fun beginResultState(){

        //Maybe Flip Logic to come.

    }

    fun onWordSaidPressed(){

        Log.d("Results", "Word Said Pressed (VM)")

        wordSaid = !wordSaid

        resultInputState.value = Pair(0, wordSaid)

    }

    fun onWordGuessedPressed(){

        Log.d("Results", "Word Guessed Pressed (VM)")

        wordGuessed = !wordGuessed

        resultInputState.value = Pair(1, wordGuessed)

    }

    fun onPausePressed(){

        //TODO(Implement pause functionality)

        gamePaused = !gamePaused

    }

    fun onQuitPressed(){

        //TODO(Implement quit functionality)

    }

    /**
     * Updates the game object, including the new score and round number.
     * Sets the state back to Countdown and calls [beginCountDownState] to initiate the new round.
     * Also resets the result inputs.
     *
     */
    fun onNextRoundPressed(){

        val oldGame = game.value!!

        val score = calculateScore(oldGame.score, wordSaid, wordGuessed)

        game.value = oldGame.updateToNextRound(score)

        resetResultInputs()

        roundChange.value = true

        gameState.value = GameState.COUNTDOWN
        beginCountDownState()

    }

    /**
     * Calculates the new score based on whether the word was said/guessed.
     *
     * @param oldScore
     * @param wordSaid
     * @param wordGuessed
     * @return
     */
    private fun calculateScore(oldScore: GameScore, wordSaid: Boolean, wordGuessed: Boolean): GameScore{

        val oldScoreRed = oldScore.scoreRed
        val oldScoreBlue = oldScore.scoreBlue

        if((!wordSaid && !wordGuessed) || (wordSaid && wordGuessed)) return oldScore

        if(wordSaid && !wordGuessed) return GameScore(oldScoreRed +1, oldScoreBlue)

        if(!wordSaid && wordGuessed) return GameScore(oldScoreRed, oldScoreBlue + 1)

        return oldScore

    }

    private fun resetResultInputs(){

        wordSaid = false
        wordGuessed = false

    }

}