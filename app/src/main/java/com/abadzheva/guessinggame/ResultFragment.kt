package com.abadzheva.guessinggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.abadzheva.guessinggame.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    @Suppress("ktlint:standard:backing-property-naming")
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ResultViewModel
    lateinit var viewModelFactory: ResultViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root

        val result = ResultFragmentArgs.fromBundle(requireArguments()).result
        viewModelFactory = ResultViewModelFactory(result)
        viewModel = ViewModelProvider(this, viewModelFactory)[ResultViewModel::class.java]

        binding.resultViewModel = viewModel

        binding.newGameButton.setOnClickListener {
            view
                .findNavController()
                .navigate(R.id.action_resultFragment_to_gameFragment)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
