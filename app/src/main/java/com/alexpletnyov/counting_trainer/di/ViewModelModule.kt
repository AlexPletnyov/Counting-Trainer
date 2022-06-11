package com.alexpletnyov.counting_trainer.di

import androidx.lifecycle.ViewModel
import com.alexpletnyov.counting_trainer.presentation.GameViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

	@Binds
	@IntoMap
	@ViewModelKey(GameViewModel::class)
	fun bindGameViewModule(viewModel: GameViewModel): ViewModel
}