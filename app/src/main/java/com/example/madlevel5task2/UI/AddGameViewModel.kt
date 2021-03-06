package com.example.madlevel5task2.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.madlevel5task2.Exception.CustomException
import com.example.madlevel5task2.Model.Game
import com.example.madlevel5task2.Repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.text.ParseException
import java.util.*

class AddGameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)
    val error = MutableLiveData<String>()
    var succes = MutableLiveData<Boolean>()

    /**
     * Add a game to the repository if the input is valid.
     */
    fun addGame(title: String, platform: String, day: String, month: String, year: String) {
        if (isValid(title, platform, day, month, year)) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    gameRepository.insertGame(
                        Game(
                            title,
                            platform,
                            dateFormatter(day.toInt(), month.toInt(), year.toInt())
                        )
                    )
                }
            }
            succes.value = true
        }
    }

    /**
     * This method checks if fields or not empty / blank. Also has try catch to check date and calls dateFormatter function
     */
    private fun isValid(
        title: String, platform: String, day: String, month: String, year: String
    ): Boolean {
        if (title.isNullOrBlank() || title == "") {
            error.value = " Please fill in a Title"
            return false
        }
        if (platform.isNullOrBlank() || platform == "") {
            error.value = " Please fill in a Platform"
            return false;
        }
        try {
            day.toInt()
            month.toInt()
            year.toInt()
        } catch (ex: NumberFormatException) {
            error.value = "Please fill in a date"
            return false
        }
        try {
            dateFormatter(day.toInt(), month.toInt(), year.toInt()).time
        } catch (ex: Exception ) {
            when(ex){
                is IllegalArgumentException,
                    is ParseException -> {
                    ex.printStackTrace()
                    error.value = "Please fill in a valid date"
                }
                else -> throw CustomException("Unknown exception")
            }
            error.value = "Please fill in a valid date"
            return false
        }
        return true
    }

    /**
     * Puts the dates in dd-MM-YYYY format, and returns a Calender object, else throws a parse exception
     */
    @Throws(ParseException::class)
    fun dateFormatter(day: Int, month: Int, year: Int): Calendar {
        val cal = Calendar.getInstance().apply {
            isLenient = false
            set(year, month - 1, day)
        }
        return cal
    }
}
