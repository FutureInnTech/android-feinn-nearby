package id.feinn.feinnnearby.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DashboardScreen(
    onEvent: (DashboardEvent) -> Unit
) {

    Scaffold(

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Button(
                onClick = {
                    onEvent(DashboardEvent.StartBroadcast)
                }
            ) {
                Text("Start Discovery")
            }

            Button(
                onClick = {
                    onEvent(DashboardEvent.StopBroadcast)
                }
            ) {
                Text("Stop Discovery")
            }

            Text(
                "Available Devices"
            )


        }
    }
}
