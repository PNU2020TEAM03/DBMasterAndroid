package com.example.dbmasterandroid.di

import android.view.View
import com.example.dbmasterandroid.ui.signup.SignUpFragment
import com.example.dbmasterandroid.ui.signup.SignUpViewModel
import com.example.dbmasterandroid.ui.splash.SplashViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val compositeDisposable = module {
    factory { CompositeDisposable() }
}

val viewModelPart = module {
    viewModel { SplashViewModel() }
    viewModel { SignUpViewModel(get(), get()) }
}