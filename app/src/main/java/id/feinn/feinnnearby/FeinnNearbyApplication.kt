package id.feinn.feinnnearby

import android.app.Application
import id.feinn.feinnnearby.utils.FeinnNotification

class FeinnNearbyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FeinnNotification.createNotificationChannels(this)

    }


}