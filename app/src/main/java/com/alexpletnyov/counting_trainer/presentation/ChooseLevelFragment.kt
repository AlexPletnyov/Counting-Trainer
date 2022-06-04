package com.alexpletnyov.counting_trainer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.alexpletnyov.counting_trainer.R
import com.alexpletnyov.counting_trainer.databinding.FragmentChooseLevelBinding
import com.alexpletnyov.counting_trainer.domain.entity.Level

class ChooseLevelFragment : Fragment() {

	private var _binding: FragmentChooseLevelBinding? = null
	private val binding: FragmentChooseLevelBinding
		get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

	private val viewModelData: DataViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		with(binding) {
			buttonLevelTest.setOnClickListener {
				launchGameFragment(Level.TEST)
			}
			buttonLevelEasy.setOnClickListener {
				launchGameFragment(Level.EASY)
			}
			buttonLevelNormal.setOnClickListener {
				launchGameFragment(Level.NORMAL)
			}
			buttonLevelHard.setOnClickListener {
				launchGameFragment(Level.HARD)
			}
		}
	}

	private fun launchGameFragment(level: Level) {
		viewModelData.setLevel(level)
		findNavController().navigate(R.id.action_chooseLevelFragment_to_gameFragment)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}