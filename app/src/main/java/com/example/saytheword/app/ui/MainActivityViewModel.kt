package com.example.saytheword.app.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saytheword.R
import com.example.saytheword.app.util.NavigationUtil
import com.example.saytheword.data.sample_data.SamplePackData
import com.example.saytheword.domain.models.Pack
import com.example.saytheword.domain.models.game.Game

class MainActivityViewModel : ViewModel() {

    var currentViewPagerPosition = 0

    //Game Handling
    val gameSetUp = Game.createNewGame(SamplePackData.packs[0], 10, 5)

    fun setGamePack(pack: Pack){

        gameSetUp.pack = pack

    }

    fun setGameRoundLength(roundLength: Int){

        gameSetUp.gameRound.roundLength = roundLength

    }

    fun setGamePointsToWin(pointsToWin: Int){

        gameSetUp.pointsToWin = pointsToWin

    }

    //Pack Select
    fun updateViewPagerPosition(position: Int){

        currentViewPagerPosition = position

    }


    //Navigation Handling

    private val backStack = ArrayDeque<Int>(mutableListOf(R.id.homeFragment))

    val navigationEvent = MutableLiveData<Int>()


    /**
     * Finds the nav action corresponding to the top of the stack and the specified destination.
     * If it exists we set it as [navigationEvent]'s value and add the destination to the top of the stack.
     *
     * @param destination The id of the fragment to which we navigate
     */
    fun navigateForwards(destination: Int) {

        val origin = backStack.last()

        val action = findNavigationAction(origin, destination)

        if (action != -1) {

            navigationEvent.value = action

            backStack.add(destination)

        }


    }

    fun navigateHome(clearBackStack: Boolean){

        val action = R.id.action_global_homeFragment

        if(clearBackStack) backStack.clear()

        navigationEvent.value = action
        backStack.add(R.id.homeFragment)

    }

    /**
     * If the backStack contains only one element there is no backwards navigation possible, so we set [navigationEvent] to -1
     * Otherwise, we use the last two elements and origin/destination, pop the stack and find the fitting navAction, which
     * we then set as the value for [navigationEvent]
     *
     */
    fun navigateBackwards() {

        if (backStack.size <= 1) {

            navigationEvent.value = -1
            return

        }

        val origin = backStack.last()

        backStack.removeLastOrNull()

        val destination = backStack.last()

        val action = findNavigationAction(origin, destination)

        if (action != -1) {

            navigationEvent.value = action

        }


    }


    /**
     *
     * @param origin The id of the fragment from which we are navigating.
     * @param destination The id of the fragment to which we are navigating
     * @return The id of the navAction to execute the navigation, or -1 if it doesn't exist.
     */
    private fun findNavigationAction(origin: Int, destination: Int): Int {

        return NavigationUtil.navigationActionHashMap.getOrDefault(Pair(origin, destination), -1)

    }


}