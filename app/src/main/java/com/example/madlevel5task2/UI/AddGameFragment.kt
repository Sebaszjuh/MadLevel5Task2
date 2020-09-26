package com.example.madlevel5task2.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.madlevel5task2.R
import kotlinx.android.synthetic.main.fragment_add_game.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddGameFragment : Fragment() {


    private val viewModel: AddGameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }


    private fun initViews() {
        fabAdd.setOnClickListener {
            // When save is clicked, save the game using the viewModel.
            viewModel.addGame(
                etTitle.text.toString(),
                etPlatform.text.toString(),
                etDay.text.toString(),
                etMonth.text.toString(),
                etYear.text.toString()
            )
            findNavController().popBackStack()
        }
    }


}