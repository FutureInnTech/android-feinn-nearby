package id.feinn.feinnnearby.ui.createAccount

import androidx.navigation3.runtime.EntryProviderScope
import id.feinn.feinnnearby.ui.NavScreen

fun EntryProviderScope<NavScreen>.createAccountScreen(
    onWrittenDownClicked: () -> Unit = {},
) {
    entry<NavScreen.CreateAccountScreen> {

        CreateAccountScreen(
            onWrittenDownClicked = onWrittenDownClicked
        )
    }
}
