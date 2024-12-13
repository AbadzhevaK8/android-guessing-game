package com.abadzheva.guessinggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.abadzheva.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    @Suppress("ktlint:standard:backing-property-naming")
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val words = listOf("Apple", "Banana", "Orange", "Grapes", "Watermelon")
    private val secretWord = words.random().uppercase()
    private var secretWordDisplay = ""
    private var correctGuesses = ""
    private var incorrectGuesses = ""
    private var livesLeft = 8

    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // start the game
        secretWordDisplay = deriveSecretWordDisplay()
        updateScreen()

        // listen for button click
        binding.guessButton.setOnClickListener {
            makeGuess(
                binding.guess.text
                    .toString()
                    .uppercase(),
            )
            binding.guess.text = null
            updateScreen()
            if (isWon() || isLost()) {
                val action =
                    GameFragmentDirections.actionGameFragmentToResultFragment(wonLostMessage())
                view.findNavController().navigate(action)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isWon(): Boolean = secretWord.equals(secretWordDisplay, true)

    private fun isLost(): Boolean = livesLeft <= 0

    private fun wonLostMessage(): String {
        var message = ""
        if (isWon()) {
            message = "You won!"
        } else if (isLost()) {
            message = "You lost!"
        }
        message += " The word was $secretWord."
        return message
    }

    private fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretWord.contains(guess)) {
                correctGuesses += guess
                secretWordDisplay = deriveSecretWordDisplay()
            } else {
                incorrectGuesses += "$guess "
                livesLeft--
            }
        }
    }

    private fun updateScreen() {
        binding.word.text = secretWordDisplay
        binding.lives.text = getString(R.string.you_have_lives_left, livesLeft)
        binding.incorrectGuesses.text = getString(R.string.incorrect_guesses, incorrectGuesses)
    }

    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    private fun checkLetter(str: String) =
        when (correctGuesses.contains(str)) {
            true -> str
            false -> "_"
        }
}
