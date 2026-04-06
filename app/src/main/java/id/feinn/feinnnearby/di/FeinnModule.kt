package id.feinn.feinnnearby.di

import id.feinn.feinnnearby.data.service.communication.CommunicationNearbyManager
import id.feinn.feinnnearby.ui.NavigationViewModel
import id.feinn.feinnnearby.ui.createAccount.CreateAccountViewModel
import id.feinn.feinnnearby.ui.dashboard.DashboardViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object FeinnModule {

    val viewModel = module {
        viewModelOf(::NavigationViewModel)
        viewModelOf(::CreateAccountViewModel)
        viewModelOf(::DashboardViewModel)
    }

    val data = module {
        single { CommunicationNearbyManager(get()) }
    }

}