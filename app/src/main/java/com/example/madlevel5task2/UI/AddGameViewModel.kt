package com.example.madlevel5task2.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.madlevel5task2.Model.Game
import com.example.madlevel5task2.Repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddGameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)


    /**
     * Add a game to the repository if the input is valid.
     */
    fun addGame(title: String, platform: String, day: String, month: String, year: String) {

        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(
                    Game(
                        title,
                        platform,
                        day.toInt(),
                        month.toInt(),
                        year.toInt()
                    )
                )

            }


        }
    }

}