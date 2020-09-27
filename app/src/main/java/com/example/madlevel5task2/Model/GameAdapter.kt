package com.example.madlevel5task2.Model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.R
import kotlinx.android.synthetic.main.item_game.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(games[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            itemView.tvTitle.text = game.title
            itemView.tvPlatform.text = game.platform
            itemView.tvDate.text =
                context.getString(R.string.release_date, dateFormat(game.release.time) )
        }
    }

    fun dateFormat(date: Date): String{
        return SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault()).format(date)
    }

    @Throws(ParseException::class)
    fun dateFormatter(day: Int, month: Int, year: Int): Calendar {
//        val dateStr: String = String.format("%s-%s-%s", day, month, year)
//        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
//        val date = formatter.parse(dateStr)
        val cal = Calendar.getInstance().apply {
            isLenient = false
            set(year, month - 1, day)
        }
        return cal
    }

}