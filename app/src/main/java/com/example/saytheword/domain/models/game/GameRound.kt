package com.example.saytheword.domain.models.game

data class GameRound(var roundNumber: Int, var turn: GameTurn, var roundLength: Int, var roundTimer: Int, val wordSaid: ArrayList<Pair<Int, Boolean>> = arrayListOf(), val wordGuessed: ArrayList<Pair<Int, Boolean>> = arrayListOf()){


    fun startRound(){

        roundNumber++

    }

    fun addWordSaidEntry(wordSaid: Boolean){

        this.wordSaid.add(Pair(roundNumber, wordSaid))

    }

    fun addWordGuessedEntry(wordGuessed: Boolean){

        this.wordGuessed.add(Pair(roundNumber, wordGuessed))

    }




}
