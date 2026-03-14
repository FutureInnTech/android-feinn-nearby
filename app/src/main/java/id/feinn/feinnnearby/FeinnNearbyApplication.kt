package id.feinn.feinnnearby

import android.app.Application
import id.feinn.feinnnearby.di.FeinnModule
import id.feinn.feinnnearby.utils.FeinnNotification
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FeinnNearbyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FeinnNearbyApplication)
            modules(FeinnModule.viewModel + FeinnModule.data)
        }

        initializeService()

    }

    private fun initializeService() {
        FeinnNotification.createNotificationChannels(this)
    }

}