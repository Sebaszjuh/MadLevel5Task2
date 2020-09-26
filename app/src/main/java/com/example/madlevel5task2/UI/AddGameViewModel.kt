package com.example.madlevel5task2.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.madlevel5task2.Model.Game
import com.example.madlevel5task2.Repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AddGameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)
    val error = MutableLiveData<String>()

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
                            day.toInt(),
                            month.toInt(),
                            year.toInt()
                        )
                    )
                }
            }
        }
    }


    private fun isValid(
        title: String, platform: String, day: String, month: String, year: String
    ): Boolean {
        if (title.isNullOrBlank() || title == "") {
            return false
        }
        if (platform.isNullOrBlank() || platform == "") {
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
            dateFormatter(day.toInt(), month.toInt(), year.toInt())
        } catch (ex: ParseException){
            ex.printStackTrace()
            error.value = "Please fill in a valid date"
            return false
        }
        return true
    }

    @Throws(ParseException::class)
    fun dateFormatter(day: Int, month: Int, year: Int): Calendar {
        val dateStr:String = String.format("%s-%s-%s", day, month, year)
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = formatter.parse(dateStr)
        val cal = Calendar.getInstance().apply {
            isLenient = false
            set(year, month-1, day)
        }
        return cal
    }
}
