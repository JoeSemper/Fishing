package com.joesemper.fishing.di

import android.content.Context
import com.joesemper.fishing.model.auth.AuthManager
import com.joesemper.fishing.model.auth.FirebaseAuthManagerImpl
import com.joesemper.fishing.model.db.CloudFireStoreDatabaseImpl
import com.joesemper.fishing.model.db.DatabaseProvider
import com.joesemper.fishing.model.repository.groups.GroupsRepository
import com.joesemper.fishing.model.repository.groups.GroupsRepositoryImpl
import com.joesemper.fishing.model.repository.weather.WeatherRepository
import com.joesemper.fishing.model.repository.weather.WeatherRetrofitImplementation
import com.joesemper.fishing.view.fragments.GroupsFragment
import com.joesemper.fishing.view.activities.SplashActivity
import com.joesemper.fishing.view.fragments.WeatherFragment
import com.joesemper.fishing.viewmodel.groups.GroupsViewModel
import com.joesemper.fishing.viewmodel.main.MainViewModel
import com.joesemper.fishing.viewmodel.splash.SplashViewModel
import com.joesemper.fishing.viewmodel.weather.WeatherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val appModule = module {
    single<DatabaseProvider> { CloudFireStoreDatabaseImpl(androidContext()) }
    single<AuthManager> { FirebaseAuthManagerImpl(androidContext()) }
    viewModel { MainViewModel(get()) }
}

val splashScreen = module {
    scope(named<SplashActivity>()) {
        viewModel { SplashViewModel(get()) }
    }
}

val groupsScreen = module {
    scope(named<GroupsFragment>()) {
        viewModel { GroupsViewModel(get()) }
        scoped<GroupsRepository> { GroupsRepositoryImpl(get()) }
    }
}

val weatherScreen = module {
    scope(named<WeatherFragment>()) {
        viewModel { WeatherViewModel(get()) }
        scoped<WeatherRepository> { WeatherRetrofitImplementation() }
    }

}


