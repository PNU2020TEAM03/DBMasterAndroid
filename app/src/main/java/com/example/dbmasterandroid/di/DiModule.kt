package com.example.dbmasterandroid.di

import com.example.dbmasterandroid.MainActivityViewModel
import com.example.dbmasterandroid.ui.login.LoginViewModel
import com.example.dbmasterandroid.ui.main.MainViewModel
import com.example.dbmasterandroid.ui.setting.SettingViewModel
import com.example.dbmasterandroid.ui.signup.emailauth.SignUpEmailViewModel
import com.example.dbmasterandroid.ui.signup.SignUpViewModel
import com.example.dbmasterandroid.ui.splash.SplashViewModel
import com.example.dbmasterandroid.ui.table.create.TableCreateViewModel
import com.example.dbmasterandroid.ui.table.select.TableSelectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/* ViewModel DI */
val viewModelPart = module {
    viewModel { SplashViewModel() }
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { TableSelectViewModel(get()) }
    viewModel { TableCreateViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { MainActivityViewModel(get()) }
    viewModel { SettingViewModel() }
    viewModel { SignUpEmailViewModel(get()) }

}