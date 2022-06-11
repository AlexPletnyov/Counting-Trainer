package com.alexpletnyov.counting_trainer.di

import android.app.Application
import com.alexpletnyov.counting_trainer.presentation.ChooseLevelFragment
import com.alexpletnyov.counting_trainer.presentation.GameFinishedFragment
import com.alexpletnyov.counting_trainer.presentation.GameFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
	modules = [
		DataModule::class,
		ViewModelModule::class
	]
)
interface ApplicationComponent {

	fun inject(fragment: ChooseLevelFragment)

	fun inject(fragment: GameFragment)

	fun inject(fragment: GameFinishedFragment)

	@Component.Factory
	interface Factory {

		fun create(
			@BindsInstance application: Application
		): ApplicationComponent
	}
}