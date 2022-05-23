package com.alexpletnyov.counting_trainer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alexpletnyov.counting_trainer.R
import com.alexpletnyov.counting_trainer.databinding.FragmentGameFinishedBinding
import com.alexpletnyov.counting_trainer.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

	private val args by navArgs<GameFinishedFragmentArgs>()

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
				args.gameResult.gameSettings.minCountOfRightAnswers
			)
			tvScoreAnswers.text = String.format(
				getString(R.string.score_answers),
				args.gameResult.countOfRightAnswers
			)
			tvRequiredPercentage.text = String.format(
				getString(R.string.required_percentage),
				args.gameResult.gameSettings.minPercentOfRightAnswers
			)
			tvScorePercentage.text = String.format(
				getString(R.string.score_percentage),
				getPercentOfRightAnswers()
			)
		}
	}

	private fun getPercentOfRightAnswers() = with(args.gameResult) {
		if (countOfQuestions == 0) {
			0
		} else {
			((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
		}
	}

	private fun getSmileId(): Int {
		return if (args.gameResult.winner) {
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

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun retryGame() {
		findNavController().popBackStack()
	}
}