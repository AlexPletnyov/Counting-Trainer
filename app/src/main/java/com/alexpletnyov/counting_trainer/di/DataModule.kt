package com.alexpletnyov.counting_trainer.di

import com.alexpletnyov.counting_trainer.data.GameRepositoryImpl
import com.alexpletnyov.counting_trainer.domain.repository.GameRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

	@ApplicationScope
	@Binds
	fun bindGameRepository(impl: GameRepositoryImpl): GameRepository
}