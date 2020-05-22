package com.example.dbmasterandroid.di

import com.example.dbmasterandroid.MainActivityViewModel
import com.example.dbmasterandroid.ui.login.LoginViewModel
import com.example.dbmasterandroid.ui.main.MainViewModel
import com.example.dbmasterandroid.ui.signup.SignUpViewModel
import com.example.dbmasterandroid.ui.splash.SplashViewModel
import com.example.dbmasterandroid.ui.table.create.TableCreateViewModel
import com.example.dbmasterandroid.ui.table.select.TableSelectViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val compositeDisposable = module {
    factory { CompositeDisposable() }
}

val viewModelPart = module {
    viewModel { SplashViewModel() }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { TableSelectViewModel(get(), get()) }
    viewModel { TableCreateViewModel(get(), get(), get()) }
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { MainActivityViewModel(get()) }
}