package com.example.dbmasterandroid.di

import com.example.dbmasterandroid.MainActivityViewModel
import com.example.dbmasterandroid.ui.custom.CustomQueryViewModel
import com.example.dbmasterandroid.ui.export.DataExportViewModel
import com.example.dbmasterandroid.ui.insert.DataInsertViewModel
import com.example.dbmasterandroid.ui.join.TableJoinViewModel
import com.example.dbmasterandroid.ui.login.LoginViewModel
import com.example.dbmasterandroid.ui.main.MainViewModel
import com.example.dbmasterandroid.ui.main.tabledata.TableDataViewModel
import com.example.dbmasterandroid.ui.setting.SettingViewModel
import com.example.dbmasterandroid.ui.setting.drop.SettingDropViewModel
import com.example.dbmasterandroid.ui.setting.pw.SettingPasswordViewModel
import com.example.dbmasterandroid.ui.setting.tablename.SettingNameViewModel
import com.example.dbmasterandroid.ui.signup.emailauth.SignUpEmailViewModel
import com.example.dbmasterandroid.ui.signup.SignUpViewModel
import com.example.dbmasterandroid.ui.splash.SplashViewModel
import com.example.dbmasterandroid.ui.table.control.TableControlViewModel
import com.example.dbmasterandroid.ui.table.create.TableCreateViewModel
import com.example.dbmasterandroid.ui.table.select.TableSelectViewModel
import com.example.dbmasterandroid.ui.update.DataUpdateViewModel
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
    viewModel { SettingPasswordViewModel(get(), get()) }
    viewModel { SettingNameViewModel(get(), get()) }
    viewModel { SettingDropViewModel(get(), get()) }
    viewModel { SignUpEmailViewModel(get()) }
    viewModel { TableDataViewModel(get(), get(), get()) }
    viewModel { TableControlViewModel() }
    viewModel { TableJoinViewModel(get(), get()) }
    viewModel { DataInsertViewModel(get(), get()) }
    viewModel { DataExportViewModel(get(), get()) }
    viewModel { DataUpdateViewModel(get(), get()) }
    viewModel { CustomQueryViewModel() }
}