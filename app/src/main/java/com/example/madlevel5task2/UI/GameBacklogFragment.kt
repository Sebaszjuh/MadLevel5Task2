package com.example.madlevel5task2.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.Model.Game
import com.example.madlevel5task2.Model.GameAdapter
import com.example.madlevel5task2.R
import kotlinx.android.synthetic.main.fragment_game_backlog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameBacklogFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()
    private var games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_backlog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeAddReminderResult()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_delete -> {
                viewModel.deleteGames()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        rvGameBacklog.adapter = gameAdapter
        rvGameBacklog.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        createItemTouchHelper().attachToRecyclerView(rvGameBacklog)

        viewModel.games.observe(viewLifecycleOwner, Observer {
            games.clear()
            games.addAll(it)
            gameAdapter.notifyDataSetChanged()
        })
    }

    private fun observeAddReminderResult() {
        viewModel.games.observe(viewLifecycleOwner, Observer { games ->
            this@GameBacklogFragment.games.clear()
            this@GameBacklogFragment.games.addAll(games)
            gameAdapter.notifyDataSetChanged()
        })
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gameToDelete = games[position]
                viewModel.deleteGame(gameToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }

}