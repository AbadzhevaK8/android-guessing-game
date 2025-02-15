package com.abadzheva.guessinggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

class ResultFragment : Fragment() {
    @Suppress("ktlint:standard:backing-property-naming")
    lateinit var viewModel: ResultViewModel
    lateinit var viewModelFactory: ResultViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val result = ResultFragmentArgs.fromBundle(requireArguments()).result
        viewModelFactory = ResultViewModelFactory(result)
        viewModel = ViewModelProvider(this, viewModelFactory)[ResultViewModel::class.java]

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Surface {
                        view?.let { ResultFragmentContent(it, viewModel) }
                    }
                }
            }
        }
    }

    @Suppress("ktlint:standard:function-naming")
    @Composable
    fun ResultText(result: String) {
        Text(
            text = result,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
        )
    }

    @Suppress("ktlint:standard:function-naming")
    @Composable
    fun NewGameButton(clicked: () -> Unit) {
        Button(onClick = clicked) {
            Text("Start New Game")
        }
    }

    @Suppress("ktlint:standard:function-naming")
    @Composable
    private fun ResultFragmentContent(
        view: View,
        viewModel: ResultViewModel,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ResultText(viewModel.result)
            NewGameButton {
                view.findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
            }
        }
    }
}
