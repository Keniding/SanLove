package com.keniding.sanlove.di

import com.keniding.sanlove.data.datasource.LocalMessageDataSource
import com.keniding.sanlove.data.repository.AuthRepository
import com.keniding.sanlove.data.repository.MessageRepository
import com.keniding.sanlove.data.repository.ProfileRepository
import com.keniding.sanlove.domain.repository.IMessageRepository
import com.keniding.sanlove.domain.usecase.GetValentineMessageUseCase
import com.keniding.sanlove.ui.common.viewmodel.AuthViewModel
import com.keniding.sanlove.ui.profile.screens.ProfileViewModel
import com.keniding.sanlove.ui.register.screen.RegisterViewModel
import com.keniding.sanlove.ui.valentine.screens.ValentineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // DataSources
    single { LocalMessageDataSource() }

    // Repositories
    single<IMessageRepository> { MessageRepository(get()) }
    single { ProfileRepository() }
    single { AuthRepository() }

    // UseCases
    factory { GetValentineMessageUseCase(get()) }

    // ViewModels
    viewModel { ValentineViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { AuthViewModel(get(), get()) }
}
