package id.feinn.feinnnearby.ui.components

import android.Manifest
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import id.feinn.feinnnearby.R
import id.feinn.feinnnearby.ui.theme.backgroundElevated
import id.feinn.feinnnearby.ui.theme.error
import id.feinn.feinnnearby.ui.theme.glassBorder
import id.feinn.feinnnearby.ui.theme.onSurfaceVariant
import id.feinn.feinnnearby.ui.theme.primary400

enum class PermissionType {
    BLUETOOTH, WIFI, LOCATION;

    fun getRequiredPermissions(): List<String> {
        return when (this) {
            BLUETOOTH -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    listOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_ADVERTISE, Manifest.permission.BLUETOOTH_CONNECT)
                } else {
                    listOf(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
            WIFI -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    listOf(Manifest.permission.NEARBY_WIFI_DEVICES, Manifest.permission.ACCESS_FINE_LOCATION)
                } else {
                    listOf(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
            LOCATION -> listOf(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    fun getTitleRes(): Int = when(this) {
        BLUETOOTH -> R.string.permission_bluetooth_title
        WIFI -> R.string.permission_wifi_title
        LOCATION -> R.string.permission_location_title
    }

    fun getDescriptionRes(): Int = when(this) {
        BLUETOOTH -> R.string.permission_bluetooth_description
        WIFI -> R.string.permission_wifi_description
        LOCATION -> R.string.permission_location_description
    }
}

@Composable
fun PermissionRequirementDialog(
    type: PermissionType,
    isPermanentlyDenied: Boolean = false,
    onDismiss: () -> Unit,
    onGrantClick: () -> Unit,
    onOpenSettingsClick: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = backgroundElevated,
            border = BorderStroke(1.dp, glassBorder)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_warning_amber),
                    contentDescription = null,
                    tint = error,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(type.getTitleRes()),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (isPermanentlyDenied) {
                        stringResource(R.string.permission_permanently_denied_description)
                    } else {
                        stringResource(type.getDescriptionRes())
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = if (isPermanentlyDenied) onOpenSettingsClick else onGrantClick,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primary400),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = if (isPermanentlyDenied) {
                            stringResource(R.string.permission_button_open_settings)
                        } else {
                            stringResource(R.string.permission_button_grant)
                        },
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = stringResource(R.string.permission_button_maybe_later),
                        color = onSurfaceVariant
                    )
                }
            }
        }
    }
}
