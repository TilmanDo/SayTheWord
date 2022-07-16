package com.example.saytheword.app.util

import com.example.saytheword.R

object NavigationUtil {


    val navigationActionHashMap : HashMap<Pair<Int, Int>, Int> = hashMapOf(
        Pair(R.id.homeFragment, R.id.manualFragment) to R.id.action_homeFragment_to_manualFragment,
        Pair(R.id.homeFragment, R.id.shopFragment) to R.id.action_homeFragment_to_shopFragment,
        Pair(R.id.homeFragment, R.id.packSelectFragment) to R.id.action_homeFragment_to_packSelectFragment,
        Pair(R.id.manualFragment, R.id.homeFragment) to R.id.action_manualFragment_to_homeFragment,
        Pair(R.id.shopFragment, R.id.homeFragment) to R.id.action_shopFragment_to_homeFragment,
        Pair(R.id.packSelectFragment, R.id.homeFragment) to R.id.action_packSelectFragment_to_homeFragment,
        Pair(R.id.packSelectFragment, R.id.newGameSettingsFragment) to R.id.action_packSelectFragment_to_newGameSettingsFragment,
        Pair(R.id.newGameSettingsFragment, R.id.packSelectFragment) to R.id.action_newGameSettingsFragment_to_packSelectFragment

    )

}