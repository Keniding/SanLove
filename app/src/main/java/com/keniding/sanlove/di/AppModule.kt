package com.keniding.sanlove.di

import com.keniding.sanlove.data.datasource.LocalMessageDataSource
import com.keniding.sanlove.data.repository.MessageRepository
import com.keniding.sanlove.data.repository.ProfileRepository
import com.keniding.sanlove.domain.repository.IMessageRepository
import com.keniding.sanlove.domain.usecase.GetValentineMessageUseCase
import com.keniding.sanlove.ui.profile.screens.ProfileViewModel
import com.keniding.sanlove.ui.valentine.screens.ValentineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // DataSources
    single { LocalMessageDataSource() }

    // Repositories
    single<IMessageRepository> { MessageRepository(get()) }
    single { ProfileRepository() }

    // UseCases
    factory { GetValentineMessageUseCase(get()) }

    // ViewModels
    viewModel { ValentineViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}
