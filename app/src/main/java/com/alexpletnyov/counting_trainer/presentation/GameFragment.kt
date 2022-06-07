package com.alexpletnyov.counting_trainer.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alexpletnyov.counting_trainer.R
import com.alexpletnyov.counting_trainer.databinding.FragmentGameBinding
import com.alexpletnyov.counting_trainer.domain.entity.GameResult
import com.alexpletnyov.counting_trainer.domain.entity.Level
import com.alexpletnyov.counting_trainer.domain.entity.Question

class GameFragment : Fragment() {

	private lateinit var level: Level

	private val viewModel by activityViewModels<GameViewModel>{
		(requireActivity() as MainActivity).factory!!
	}

	private val tvOptions by lazy {
		mutableListOf<TextView>().apply {
			add(binding.tvOption1)
			add(binding.tvOption2)
			add(binding.tvOption3)
			add(binding.tvOption4)
			add(binding.tvOption5)
			add(binding.tvOption6)
		}
	}

	private var _binding: FragmentGameBinding? = null
	private val binding: FragmentGameBinding
		get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		getArgs()
		viewModel.startGame(level)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGameBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observeViewModel()
		setClickListenersToOptions()
	}

	private fun setClickListenersToOptions() {
		for (tvOption in tvOptions) {
			tvOption.setOnClickListener {
				viewModel.chooseAnswer(tvOption.text.toString().toInt())
			}
		}
	}

	private fun observeViewModel() {
		viewModel.question.observe(viewLifecycleOwner) {
			binding.tvSum.text = it.sum.toString()
			binding.tvLeftNumber.text = it.visibleNumber.toString()
			for (i in 0 until tvOptions.size) {
				tvOptions[i].text = it.options[i].toString()
			}
		}
		viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
			binding.progressBar.setProgress(it, true)
		}
		viewModel.enoughCount.observe(viewLifecycleOwner) {
			binding.tvAnswersProgress.setTextColor(getColorByState(it))
		}
		viewModel.enoughPercent.observe(viewLifecycleOwner) {
			val color = getColorByState(it)
			binding.progressBar.progressTintList = ColorStateList.valueOf(color)
		}
		viewModel.minPercent.observe(viewLifecycleOwner) {
			binding.progressBar.secondaryProgress = it
		}
		//TODO clear gameResult?
		viewModel.gameResult.observe(viewLifecycleOwner) {
			launchGameFinishedFragment()
		}
		viewModel.progressAnswers.observe(viewLifecycleOwner) {
			binding.tvAnswersProgress.text = it
		}
		viewModel.formattedTime.observe(viewLifecycleOwner) {
			binding.tvTimer.text = it
		}
		viewModel.timeRunningOut.observe(viewLifecycleOwner) {
			val color = getColorByState(!it)
			binding.tvTimer.backgroundTintList = ColorStateList.valueOf(color)
		}
	}

	private fun getColorByState(goodState: Boolean): Int {
		val colorId = if (goodState) {
			R.color.green
		} else {
			R.color.pink
		}
		return ContextCompat.getColor(requireContext(), colorId)
	}

	private fun launchGameFinishedFragment() {
		findNavController().navigate(R.id.action_gameFragment_to_gameFinishedFragment)
	}

	private fun getArgs() {
		viewModel.level.observe(activity as LifecycleOwner) {
			level = it
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}