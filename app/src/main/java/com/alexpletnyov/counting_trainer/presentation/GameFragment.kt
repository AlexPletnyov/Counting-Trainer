package com.alexpletnyov.counting_trainer.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.alexpletnyov.counting_trainer.R
import com.alexpletnyov.counting_trainer.databinding.FragmentGameBinding
import javax.inject.Inject

class GameFragment : Fragment() {

	@Inject
	lateinit var viewModelFactory: ViewModelFactory

	private val component by lazy {
		(requireActivity().application as GameApplication).component
	}

	private val viewModel by activityViewModels<GameViewModel> {
		viewModelFactory
	}

	private val tvOptions by lazy {
		with(binding) {
			listOf(
				tvOption1,
				tvOption2,
				tvOption3,
				tvOption4,
				tvOption5,
				tvOption6
			)
		}
	}

	private var _binding: FragmentGameBinding? = null
	private val binding: FragmentGameBinding
		get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

	override fun onAttach(context: Context) {
		super.onAttach(context)
		component.inject(this)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.startGame()
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
		tvOptions.forEachIndexed { index, item ->
			item.setOnClickListener {
				viewModel.chooseAnswer(index)
			}
		}
	}

	private fun observeViewModel() {
		with(viewModel) {
			question.observe(viewLifecycleOwner) {
				binding.tvSum.text = it.sum.toString()
				binding.tvLeftNumber.text = it.visibleNumber.toString()
				tvOptions.forEachIndexed { index, _ ->
					tvOptions[index].text = it.options[index].toString()
				}
			}
			percentOfRightAnswers.observe(viewLifecycleOwner) {
				binding.progressBar.setProgress(it, true)
			}
			enoughCount.observe(viewLifecycleOwner) {
				binding.tvAnswersProgress.setTextColor(getColorByState(it))
			}
			enoughPercent.observe(viewLifecycleOwner) {
				val color = getColorByState(it)
				binding.progressBar.progressTintList = ColorStateList.valueOf(color)
			}
			minPercent.observe(viewLifecycleOwner) {
				binding.progressBar.secondaryProgress = it
			}
			finished.observe(viewLifecycleOwner) {
				if (it) launchGameFinishedFragment()
			}
			progressAnswers.observe(viewLifecycleOwner) {
				binding.tvAnswersProgress.text = it
			}
			formattedTime.observe(viewLifecycleOwner) {
				binding.tvTimer.text = it
			}
			timeRunningOut.observe(viewLifecycleOwner) {
				val color = getColorByState(!it)
				binding.tvTimer.backgroundTintList = ColorStateList.valueOf(color)
			}
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

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}