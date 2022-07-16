package com.example.saytheword.app.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.saytheword.R
import com.example.saytheword.app.util.NavigationUtil

class MainActivityViewModel : ViewModel() {


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