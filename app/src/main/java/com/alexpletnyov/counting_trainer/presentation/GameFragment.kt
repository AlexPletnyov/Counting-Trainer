package com.alexpletnyov.counting_trainer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexpletnyov.counting_trainer.R
import com.alexpletnyov.counting_trainer.databinding.FragmentGameBinding
import com.alexpletnyov.counting_trainer.domain.entity.GameResult
import com.alexpletnyov.counting_trainer.domain.entity.GameSettings
import com.alexpletnyov.counting_trainer.domain.entity.Level

class GameFragment : Fragment() {

	private lateinit var level: Level
	private var _binding: FragmentGameBinding? = null
	private val binding: FragmentGameBinding
		get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		parseArgs()
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
		binding.tvSum.setOnClickListener {
			launchGameFinishedFragment(
				GameResult(
					true,
					10,
					5,
					GameSettings(0, 0, 0, 0)
				)
			)
		}
	}

	private fun launchGameFinishedFragment(gameResult: GameResult) {
		requireActivity().supportFragmentManager.beginTransaction()
			.replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
			.addToBackStack(null)
			.commit()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun parseArgs() {
		requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
			level = it
		}
	}

	companion object {

		private const val KEY_LEVEL = "level"
		const val NAME = "GameFragment"

		fun newInstance(level: Level): GameFragment {
			return GameFragment().apply {
				arguments = Bundle().apply {
					putParcelable(KEY_LEVEL, level)
				}
			}
		}
	}
}