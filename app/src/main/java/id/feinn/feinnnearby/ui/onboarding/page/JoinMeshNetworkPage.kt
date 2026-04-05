package id.feinn.feinnnearby.ui.onboarding.page

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import id.feinn.feinnnearby.R
import id.feinn.feinnnearby.ui.components.PagerIndicator
import id.feinn.feinnnearby.ui.components.PermissionRequirementDialog
import id.feinn.feinnnearby.ui.components.PermissionType
import id.feinn.feinnnearby.ui.theme.FeinnNearbyTheme
import id.feinn.feinnnearby.ui.theme.background
import id.feinn.feinnnearby.ui.theme.backgroundElevated
import id.feinn.feinnnearby.ui.theme.error
import id.feinn.feinnnearby.ui.theme.glassBorder
import id.feinn.feinnnearby.ui.theme.onPrimary
import id.feinn.feinnnearby.ui.theme.onSurfaceVariant
import id.feinn.feinnnearby.ui.theme.primary400
import id.feinn.feinnnearby.ui.theme.primary600

@Composable
fun JoinMeshNetworkPage(
    modifier: Modifier = Modifier,
    onConnectClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val activity = context as? Activity

    // --- State Management ---
    val bluetoothManager = remember { context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager }
    val bluetoothAdapter = remember { bluetoothManager?.adapter }
    var isBluetoothEnabled by remember { mutableStateOf(bluetoothAdapter?.isEnabled == true) }

    val wifiManager = remember { context.applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager }
    var isWifiEnabled by remember { mutableStateOf(wifiManager?.isWifiEnabled == true) }

    val locationManager = remember { context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager }
    var isLocationEnabled by remember { mutableStateOf(locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) }

    var showPermissionDialog by remember { mutableStateOf<PermissionType?>(null) }
    var isPermanentlyDenied by remember { mutableStateOf(false) }

    // --- Connectivity Monitoring ---
    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    BluetoothAdapter.ACTION_STATE_CHANGED -> {
                        val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                        isBluetoothEnabled = (state == BluetoothAdapter.STATE_ON)
                    }
                    WifiManager.WIFI_STATE_CHANGED_ACTION -> {
                        val state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
                        isWifiEnabled = (state == WifiManager.WIFI_STATE_ENABLED)
                    }
                    LocationManager.PROVIDERS_CHANGED_ACTION -> {
                        isLocationEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true
                    }
                }
            }
        }
        val filter = IntentFilter().apply {
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
            addAction(LocationManager.PROVIDERS_CHANGED_ACTION)
        }
        context.registerReceiver(receiver, filter)
        onDispose { context.unregisterReceiver(receiver) }
    }

    // --- Activity Result Launchers ---
    val enableLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    val openSettingsLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        if (results.values.all { it }) {
            showPermissionDialog?.let { launchActivation(it, context, enableLauncher) }
        }
        showPermissionDialog = null
    }

    // --- Action Handlers ---
    val handleCheckAndRequest = { type: PermissionType ->
        val permissions = type.getRequiredPermissions()
        val missing = permissions.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }

        if (missing.isEmpty()) {
            launchActivation(type, context, enableLauncher)
        } else {
            isPermanentlyDenied = missing.any { permission ->
                activity != null && !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
            }
            showPermissionDialog = type
        }
    }

    // --- UI Structure ---
    Box(modifier = modifier.fillMaxSize().background(background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderSection()
            
            Spacer(modifier = Modifier.height(48.dp))

            ConnectivityCardsSection(
                isLocationEnabled = isLocationEnabled,
                isWifiEnabled = isWifiEnabled,
                isBluetoothEnabled = isBluetoothEnabled,
                onRequest = handleCheckAndRequest
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(48.dp))

            FooterSection(
                isEnabled = isBluetoothEnabled && isWifiEnabled && isLocationEnabled,
                onConnectClick = onConnectClick
            )
        }

        // --- Overlays ---
        showPermissionDialog?.let { type ->
            PermissionRequirementDialog(
                type = type,
                isPermanentlyDenied = isPermanentlyDenied,
                onDismiss = { showPermissionDialog = null },
                onGrantClick = { permissionLauncher.launch(type.getRequiredPermissions().toTypedArray()) },
                onOpenSettingsClick = {
                    openAppSettings(context, openSettingsLauncher)
                    showPermissionDialog = null
                }
            )
        }
    }
}

private fun launchActivation(type: PermissionType, context: Context, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
    val intent = when (type) {
        PermissionType.BLUETOOTH -> Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        PermissionType.WIFI -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) Intent(Settings.Panel.ACTION_WIFI)
            else Intent(Settings.ACTION_WIFI_SETTINGS)
        }
        PermissionType.LOCATION -> Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    }
    launcher.launch(intent)
}

private fun openAppSettings(context: Context, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", context.packageName, null))
    launcher.launch(intent)
}

@Composable
private fun HeaderSection() {
    Text(
        text = stringResource(R.string.join_mesh_title),
        style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = Color.White
        ),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(R.string.join_mesh_subtitle),
        style = MaterialTheme.typography.bodyMedium.copy(color = onSurfaceVariant, lineHeight = 22.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun ConnectivityCardsSection(
    isLocationEnabled: Boolean,
    isWifiEnabled: Boolean,
    isBluetoothEnabled: Boolean,
    onRequest: (PermissionType) -> Unit
) {
    ConnectivityCardItem(
        iconRes = R.drawable.ic_location,
        title = stringResource(R.string.permission_location_title),
        description = stringResource(R.string.permission_location_description),
        statusOn = isLocationEnabled,
        activateText = stringResource(R.string.join_mesh_location_activate),
        authorizedText = stringResource(R.string.join_mesh_location_authorized),
        onClick = { onRequest(PermissionType.LOCATION) }
    )
    Spacer(modifier = Modifier.height(24.dp))
    ConnectivityCardItem(
        iconRes = R.drawable.ic_wifi,
        title = stringResource(R.string.join_mesh_wifi_title),
        description = stringResource(R.string.join_mesh_wifi_description),
        statusOn = isWifiEnabled,
        activateText = stringResource(R.string.join_mesh_wifi_activate),
        authorizedText = stringResource(R.string.join_mesh_wifi_authorized),
        onClick = { onRequest(PermissionType.WIFI) }
    )
    Spacer(modifier = Modifier.height(24.dp))
    ConnectivityCardItem(
        iconRes = R.drawable.ic_bluetooth,
        title = stringResource(R.string.join_mesh_ble_title),
        description = stringResource(R.string.join_mesh_ble_description),
        statusOn = isBluetoothEnabled,
        activateText = stringResource(R.string.join_mesh_ble_activate),
        authorizedText = stringResource(R.string.join_mesh_ble_authorized),
        onClick = { onRequest(PermissionType.BLUETOOTH) }
    )
}

@Composable
private fun ConnectivityCardItem(
    iconRes: Int,
    title: String,
    description: String,
    statusOn: Boolean,
    activateText: String,
    authorizedText: String,
    onClick: () -> Unit
) {
    ConnectivityCard(
        iconRes = iconRes,
        title = title,
        description = description,
        statusText = stringResource(if (statusOn) R.string.join_mesh_status_on else R.string.join_mesh_status_off),
        isStatusOn = statusOn,
        buttonContent = {
            if (!statusOn) {
                Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, glassBorder)
                ) {
                    Text(text = activateText, color = primary400, fontWeight = FontWeight.SemiBold)
                }
            } else {
                AuthorizedBadge(text = authorizedText)
            }
        }
    )
}

@Composable
private fun FooterSection(
    isEnabled: Boolean,
    onConnectClick: () -> Unit
) {
    Button(
        onClick = onConnectClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = primary400, contentColor = onPrimary),
        shape = RoundedCornerShape(28.dp),
        enabled = isEnabled
    ) {
        Text(
            text = stringResource(R.string.join_mesh_button_connect),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        text = stringResource(R.string.join_mesh_footer),
        style = MaterialTheme.typography.labelSmall.copy(color = onSurfaceVariant.copy(alpha = 0.5f), letterSpacing = 0.5.sp),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(48.dp))
    PagerIndicator(currentPage = 1, pageCount = 3)
}

@Composable
private fun AuthorizedBadge(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF132328))
            .border(1.dp, Color(0xFF1B4332).copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.ic_shield_check), contentDescription = null, tint = Color(0xFF4ADE80), modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Color(0xFF4ADE80), fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun ConnectivityCard(
    modifier: Modifier = Modifier,
    iconRes: Int,
    title: String,
    description: String,
    statusText: String,
    isStatusOn: Boolean,
    buttonContent: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).background(backgroundElevated).padding(24.dp)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Color(0xFF1E293B)), contentAlignment = Alignment.Center) {
                    Icon(painter = painterResource(iconRes), contentDescription = title, tint = primary400, modifier = Modifier.size(24.dp))
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(if (isStatusOn) primary600 else error))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = statusText, style = MaterialTheme.typography.labelMedium.copy(color = if (isStatusOn) primary600 else error, fontWeight = FontWeight.Bold))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = Color.White))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, style = MaterialTheme.typography.bodyMedium.copy(color = onSurfaceVariant, lineHeight = 20.sp))
            Spacer(modifier = Modifier.height(24.dp))
            buttonContent()
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF0A0E1A)
private fun PreviewJoinMeshNetworkPage() {
    FeinnNearbyTheme {
        JoinMeshNetworkPage()
    }
}
