package com.alexpletnyov.counting_trainer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.alexpletnyov.counting_trainer.R
import com.alexpletnyov.counting_trainer.databinding.FragmentGameFinishedBinding
import com.alexpletnyov.counting_trainer.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

	private lateinit var gameResult: GameResult
	private val viewModel by activityViewModels<GameViewModel>{
		(requireActivity() as MainActivity).factory!!
	}

	private var _binding: FragmentGameFinishedBinding? = null
	private val binding: FragmentGameFinishedBinding
		get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		getArgs()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupClickListener()
		bindViews()
	}

	private fun bindViews() {
		with(binding) {
			emojiResult.setImageResource(getSmileId())
			tvRequiredAnswers.text = String.format(
				getString(R.string.required_answers),
				gameResult.gameSettings.minCountOfRightAnswers
			)
			tvScoreAnswers.text = String.format(
				getString(R.string.score_answers),
				gameResult.countOfRightAnswers
			)
			tvRequiredPercentage.text = String.format(
				getString(R.string.required_percentage),
				gameResult.gameSettings.minPercentOfRightAnswers
			)
			tvScorePercentage.text = String.format(
				getString(R.string.score_percentage),
				getPercentOfRightAnswers()
			)
		}
	}

	private fun getPercentOfRightAnswers() = with(gameResult) {
		if (countOfQuestions == 0) {
			0
		} else {
			((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
		}
	}

	private fun getSmileId(): Int {
		return if (gameResult.winner) {
			R.drawable.ic_win
		} else {
			R.drawable.ic_sad
		}
	}

	private fun setupClickListener() {
		binding.buttonRetry.setOnClickListener {
			retryGame()
		}
	}

	private fun getArgs() {
		viewModel.gameResult.observe(activity as LifecycleOwner) {
			gameResult = it
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun retryGame() {
		findNavController().popBackStack()
	}
}