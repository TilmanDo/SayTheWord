package com.example.saytheword.app.ui.active_game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saytheword.data.sample_data.SamplePackData
import com.example.saytheword.domain.models.game.Game
import com.example.saytheword.domain.models.game.GameScore
import com.example.saytheword.domain.models.game.GameState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*

class ActiveGameViewModel : ViewModel() {

    lateinit var countDownTimer: CountDownTimer

    lateinit var wordTimer: CountDownTimer

    var countDownTimerActive = false

    var wordTimerActive = false

    var pausedCountDownValue = 3

    var pausedWordTimerValue = 10

    val activeGameNavOptionSelected = MutableLiveData<ActiveGameNavOptions>()

    val game = MutableLiveData<Game>(Game.createNewGame(SamplePackData.packs[0], 10, 5))

    val gameCountDownState = MutableLiveData<Int>(3)

    val wordTimerState = MutableLiveData<Int>(120)

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

        game.pack.shuffleCards()

        this.game.value = game

        beginCountDownState(3000)

    }

    /**
     * STATE 1: Countdown.
     *
     * Starts a three second timer and updates the [gameCountDownState] which is observed by the fragment.
     * Upon finishing the timer, updates the [gameState] which is observed by the fragment and begins the next state.
     *
     */
    private fun beginCountDownState(timerLength: Long) {

        game.value!!.state = GameState.COUNTDOWN

        countDownTimerActive = true

        countDownTimer = object : CountDownTimer(timerLength, 1000) {

            override fun onTick(p0: Long) {
                if (!countDownTimerActive) {
                    cancel()
                } else {
                    gameCountDownState.value = (p0 / 1000 + 1).toInt()
                }
            }

            override fun onFinish() {
                gameState.postValue(GameState.WORD)
                beginWordState((game.value!!.gameRound.roundLength * 1000).toLong())
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
    fun beginWordState(timerLength: Long) {

        game.value!!.state = GameState.WORD

        wordTimerActive = true

        wordTimer = object : CountDownTimer(timerLength, 1000) {

            override fun onTick(p0: Long) {

                Log.d("Timer", wordTimerActive.toString())

                if(!wordTimerActive){
                    cancel()
                } else {
                    wordTimerState.value = (p0 / 1000 + 1).toInt()
                }
            }

            override fun onFinish() {
                gameState.postValue(GameState.RESULT)
                beginResultState()
            }


        }.start()


    }

    fun beginResultState() {

        //There isn't any logic to handle here, I'll leave up the function for the sake of completeness.

    }

    fun onWordSaidPressed() {

        Log.d("Results", "Word Said Pressed (VM)")

        wordSaid = !wordSaid

        resultInputState.value = Pair(0, wordSaid)

    }

    fun onWordGuessedPressed() {

        Log.d("Results", "Word Guessed Pressed (VM)")

        wordGuessed = !wordGuessed

        resultInputState.value = Pair(1, wordGuessed)

    }


    /**
     * On press of the pause button, inverts the [gamePaused] field and then stops/resumes the necessary countdowns,
     * depending on [gamePaused]'s new value and the current game-state.
     *
     */
    fun onPausePressed() {

        gamePaused = !gamePaused

        if (gamePaused) {

            Log.d("Timer", "Game State: ${game.value!!.state.toString()}")

            when (game.value!!.state) {
                GameState.COUNTDOWN -> {
                    countDownTimerActive = false
                    pausedCountDownValue = gameCountDownState.value!!
                }
                GameState.WORD -> {
                    wordTimerActive = false
                    pausedWordTimerValue = wordTimerState.value!!
                    Log.d("Timer", "Setting wordTimerActive to $wordTimerActive")
                }
                GameState.RESULT -> {
                }
            }

        } else {

            when (game.value!!.state) {
                GameState.COUNTDOWN -> {
                    beginCountDownState((pausedCountDownValue * 1000).toLong())
                }
                GameState.WORD -> {
                    beginWordState((pausedWordTimerValue * 1000).toLong())
                }
                GameState.RESULT -> {
                }
            }

        }


    }

    fun quitGame(){

        countDownTimerActive = false
        wordTimerActive = false

        Log.d("Quit", "Word Said Array: " + game.value!!.gameRound.wordSaid.toString())
        Log.d("Quit", "Word Guessed Array: " + game.value!!.gameRound.wordGuessed.toString())


    }

    /**
     * Updates the game object, including the new score, round number, and word said/guessed arrayLists.
     * Sets the state back to Countdown and calls [beginCountDownState] to initiate the new round.
     * Also resets the result inputs.
     *
     */
    fun onNextRoundPressed() {

        val oldGame = game.value!!

        val score = calculateScore(oldGame.score, wordSaid, wordGuessed)

        game.value!!.gameRound.addWordSaidEntry(wordSaid)
        game.value!!.gameRound.addWordGuessedEntry(wordGuessed)

        game.value = oldGame.updateToNextRound(score)

        resetResultInputs()

        roundChange.value = true


        gameState.value = GameState.COUNTDOWN
        beginCountDownState(3000)

    }

    /**
     * Calculates the new score based on whether the word was said/guessed.
     *
     * @param oldScore
     * @param wordSaid
     * @param wordGuessed
     * @return
     */
    private fun calculateScore(
        oldScore: GameScore,
        wordSaid: Boolean,
        wordGuessed: Boolean
    ): GameScore {

        val oldScoreRed = oldScore.scoreRed
        val oldScoreBlue = oldScore.scoreBlue

        if ((!wordSaid && !wordGuessed) || (wordSaid && wordGuessed)) return oldScore

        if (wordSaid && !wordGuessed) return GameScore(oldScoreRed + 1, oldScoreBlue)

        if (!wordSaid && wordGuessed) return GameScore(oldScoreRed, oldScoreBlue + 1)

        return oldScore

    }

    private fun resetResultInputs() {

        wordSaid = false
        wordGuessed = false

    }

}