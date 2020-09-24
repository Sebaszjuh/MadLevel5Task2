package com.example.madlevel5task2.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel5task2.DAO.GameDAO
import com.example.madlevel5task2.Database.GameRoomDatabase
import com.example.madlevel5task2.Model.Game

class GameRepository(context: Context) {

    private var gameDAO: GameDAO

    init {
        val gameRoomDatabase = GameRoomDatabase.getDatabase(context)
        gameDAO = gameRoomDatabase!!.gameDao()
    }

     fun getAllGames(): LiveData<List<Game>> {
        return gameDAO.getAllGames() ?: MutableLiveData(emptyList())
    }

    suspend fun insertGame(game: Game) {
        gameDAO.insertGame(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDAO.deleteGame(game)
    }

    suspend fun deleteAllGames() {
        gameDAO.deleteAllGames()
    }


}