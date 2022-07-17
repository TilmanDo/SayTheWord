package com.example.saytheword.domain.models.game

data class GameRound(var roundNumber: Int, var turn: GameTurn, var roundLength: Int, var roundTimer: Int){


    fun startRound(){

        roundNumber++

    }


}
