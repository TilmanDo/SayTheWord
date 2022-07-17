package com.example.saytheword.app.ui.active_game.adapters

import com.example.saytheword.domain.models.game.GameState

interface ActiveGameCard {

    fun updateCardState(state: GameState)


}