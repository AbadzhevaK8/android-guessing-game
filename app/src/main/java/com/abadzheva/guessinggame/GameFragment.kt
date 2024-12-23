package com.abadzheva.guessinggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.abadzheva.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    @Suppress("ktlint:standard:backing-property-naming")
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        viewModel.incorrectGuesses.observe(
            viewLifecycleOwner,
            Observer { newValue ->
                binding.incorrectGuesses.text =
                    getString(
                        R.string.incorrect_guesses_new_value,
                        newValue,
                    )
            },
        )

        viewModel.livesLeft.observe(
            viewLifecycleOwner,
            Observer { newValue ->
                binding.lives.text =
                    getString(
                        R.string.you_have_lives_left_new_value,
                        newValue.toString(),
                    )
            },
        )

        viewModel.secretWordDisplay.observe(
            viewLifecycleOwner,
            Observer { newValue ->
                binding.word.text = newValue
            },
        )

        viewModel.gameOver.observe(
            viewLifecycleOwner,
            Observer { newValue ->
                if (newValue) {
                    val action =
                        GameFragmentDirections
                            .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                    view.findNavController().navigate(action)
                }
            },
        )

        // listen for button click
        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(
                binding.guess.text
                    .toString()
                    .uppercase(),
            )
            binding.guess.text = null
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
